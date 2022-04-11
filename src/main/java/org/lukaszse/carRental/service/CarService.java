package org.lukaszse.carRental.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lukaszse.carRental.exceptions.NotFoundException;
import org.lukaszse.carRental.model.Car;
import org.lukaszse.carRental.model.TimePeriod;
import org.lukaszse.carRental.repository.CarRepository;
import org.lukaszse.carRental.repository.CarSearchRepository;
import org.lukaszse.carRental.repository.ReservationSearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.lukaszse.carRental.service.ReservationService.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarService {

    public static final String CAR_NOT_FOUND_ERROR_MESSAGE = "Car with registrationNo=%s has not been found.";
    private final CarRepository carRepository;
    private final CarSearchRepository carSearchRepository;
    private final ReservationSearchRepository reservationSearchRepository;


    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Car getCar(Integer id) {
            return carRepository.findById(id)
                    .orElse(null);
    }

    public void addEditContractor(final Car car) {
        if(car.getId() == null) {
            addContractor(car);
        } else {
            editContractor(car);
        }
    }

    public void addContractor(Car car) {
        carRepository.save(car);
    }

    public void editContractor(Car car) {
        if(!carRepository.existsById(car.getId())) {
            throw new NotFoundException(CAR_NOT_FOUND_ERROR_MESSAGE.formatted(car.getId()));
        }
        carRepository.save(car);
    }

    public void deleteContractor(Integer Contractorid) {
        carRepository.deleteById(Contractorid);
    }

    public Page<Car> getPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        var users = carRepository.findAll();
        var contractorsPage = Stream.of(users)
                .filter(orderList -> !orderList.isEmpty())
                .flatMap(Collection::stream)
                .skip((long) currentPage * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
        return new PageImpl<Car>(contractorsPage, PageRequest.of(currentPage, pageSize), users.size());
    }

    public Page<Car> findCars(final String manufacturer, final String model, final TimePeriod timePeriod, final Pageable pageable) {
        var cars = carSearchRepository.findCarByManufacturerContainsIgnoreCaseAndModelContainsIgnoreCase(manufacturer, model, pageable);
        var availableCars = cars.stream()
                .filter(car -> isCarAvailable(car.getId(), timePeriod))
                .collect(Collectors.toList());
        return new PageImpl<>(availableCars, pageable, availableCars.size());
    }

    private boolean isCarAvailable(final Integer carId, final TimePeriod timePeriod) {
        if(timePeriod.getDateFrom() == null && timePeriod.getDateTo() == null) {
            return true;
        }
        return reservationSearchRepository.findByCar_Id(carId).stream()
                .map(reservation -> TimePeriod.of(reservation.getDateFrom(), reservation.getDateTo()))
                .noneMatch(reservationPeriod -> checkIfPeriodOverlap(timePeriod, reservationPeriod));
    }
}
