package org.lukaszse.carRental.service;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lukaszse.carRental.model.Car;
import org.lukaszse.carRental.model.Reservation;
import org.lukaszse.carRental.model.TimePeriod;
import org.lukaszse.carRental.model.User;
import org.lukaszse.carRental.model.dto.ReservationDto;
import org.lukaszse.carRental.repository.ReservationRepository;
import org.lukaszse.carRental.repository.ReservationSearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationSearchRepository reservationSearchRepository;
    private final CarService carService;
    private final UserService userService;
    private final AvailabilityService availabilityService;

    public Reservation getReservation(Integer id) {
        return reservationRepository.getById(id);
    }

    public Page<Reservation> findReservations(final String userName, final Pageable pageable) {
        return reservationSearchRepository.findByUser_UserNameContainsIgnoreCase(userName, pageable);
    }

    @Transactional
    public boolean addReservation(ReservationDto reservationDto) {
        final Reservation newReservation = createReservationOrGetReservationForUpdate(reservationDto);
        final boolean isCarAvailable = availabilityService.isCarAvailable(reservationDto.getCarId(),
                TimePeriod.of(reservationDto.getDateFrom(), reservationDto.getDateTo()));
        if(isCarAvailable) {
            reservationRepository.save(newReservation);
            return true;
        } else {
            return false;
        }
    }
    @Transactional
    public void updateReservation(ReservationDto reservationDto) {
        final Reservation reservationToUpdate = createReservationOrGetReservationForUpdate(reservationDto);
        reservationRepository.save(reservationToUpdate);
    }

    public void deleteReservation(Integer id) {
        reservationRepository.deleteById(id);
    }

    public ReservationDto prepareReservationViewDto(final String userName, final int carId, final TimePeriod timePeriod) {
        final Car car = carService.getCar(carId);
        final BigDecimal totalCost = calculateTotalCost(timePeriod, car.getCostPerDay());
        return ReservationDto.of(userName, carId, car, timePeriod, totalCost);
    }

    private Reservation createReservationOrGetReservationForUpdate(final ReservationDto reservationDto) {
        if (reservationDto.getCarId() == null) {
            reservationDto.setCarId(reservationDto.getCar().getId());
        }
        var user = userService.getUser(reservationDto.getUserName());
        var car = carService.getCar(reservationDto.getCarId());
        final TimePeriod timePeriod = TimePeriod.of(reservationDto.getDateFrom(), reservationDto.getDateTo());
        var totalCost = calculateTotalCost(reservationDto.getDateFrom(), reservationDto.getDateTo(), car.getCostPerDay());
        return reservationDto.getId() == null ?
                createReservation(reservationDto, user, car, totalCost) :
                getAndUpdateReservation(reservationDto, user, car, totalCost);
    }

    private Reservation createReservation(final ReservationDto reservationDto,
                                          final User user,
                                          final Car car,
                                          final BigDecimal totalCost) {
        return new Reservation(
                user, car, reservationDto.getDateFrom(), reservationDto.getDateTo(), totalCost);
    }

    private Reservation getAndUpdateReservation(final ReservationDto reservationDto,
                                                final User user,
                                                final Car car,
                                                final BigDecimal totalCost) {
        Reservation reservation = getReservation(reservationDto.getId());
        return Reservation.of(
                reservation.getId(),
                user,
                car,
                LocalDate.now(),
                reservationDto.getDateFrom(),
                reservationDto.getDateTo(),
                totalCost,
                reservationDto.getRented());
    }

    private static BigDecimal calculateTotalCost(final LocalDate dateFrom, final LocalDate dateTo, final BigDecimal costPerDay) {
        var daysDiff = ChronoUnit.DAYS.between(dateFrom, dateTo);
        return costPerDay.multiply(BigDecimal.valueOf(daysDiff));
    }

    private static BigDecimal calculateTotalCost(final TimePeriod timePeriod, final BigDecimal costPerDay) {
        return calculateTotalCost(timePeriod.getDateFrom(), timePeriod.getDateTo(), costPerDay);
    }
}
