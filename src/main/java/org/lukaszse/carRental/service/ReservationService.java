package org.lukaszse.carRental.service;

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

    public Reservation getReservation(Integer id) {
        return reservationRepository.getById(id);
    }

    public Page<Reservation> getAllReservations(final Pageable pageable) {
        return reservationSearchRepository.findAll(pageable);
    }

    public Page<Reservation> findReservations(final String userName, final Pageable pageable) {
        return reservationSearchRepository.findByUser_UserNameContainsIgnoreCase(userName, pageable);
    }

    public List<Reservation> findReservations(final int carId) {
        return reservationSearchRepository.findByCar_Id(carId);
    }

    public void addEditReservation(ReservationDto reservationDto) {
        reservationRepository.save(createOrderOrGetOrderForUpdate(reservationDto));
    }

    public void deleteReservation(Integer id) {
        reservationRepository.deleteById(id);
    }

    private Reservation createOrderOrGetOrderForUpdate(final ReservationDto reservationDto) {
        var user = userService.getUser(reservationDto.getUserName());
        var car = carService.getCar(reservationDto.getCarId());
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

    public static boolean checkIfPeriodOverlap(final TimePeriod timePeriod1, TimePeriod timePeriod2) {
        final LocalDate s1 = timePeriod1.getDateFrom();
        final LocalDate e1 = timePeriod1.getDateTo();
        final LocalDate s2 = timePeriod2.getDateFrom();
        final LocalDate e2 = timePeriod2.getDateTo();

        if(s1 == null || e1 == null || s2 == null || e2 == null) {
            return false;
        }

        if(s1.compareTo(s2)<0 && e1.compareTo(s2)>0 ||
                s1.compareTo(e2)<0 && e1.compareTo(e2)>0 ||
                s1.compareTo(s2)<0 && e1.compareTo(e2)>0 ||
                s1.compareTo(s2)>0 && e1.compareTo(e2)<0 )
        {
            log.info("Periods overlap! Period 1: {}, Period2: {}", timePeriod1, timePeriod2);
            return true;
        }
        else {
            return false;
        }
    }
}
