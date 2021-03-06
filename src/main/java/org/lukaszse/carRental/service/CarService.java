package org.lukaszse.carRental.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lukaszse.carRental.exceptions.NotFoundException;
import org.lukaszse.carRental.model.Car;
import org.lukaszse.carRental.model.TimePeriod;
import org.lukaszse.carRental.repository.CarRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarService {

    public static final String CAR_NOT_FOUND_ERROR_MESSAGE = "Car with registrationNo=%s has not been found.";
    private final CarRepository carRepository;
    private final AvailabilityService availabilityService;

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Car getCar(Integer id) {
            return carRepository.findById(id)
                    .orElse(null);
    }

    public void addEditCar(final Car car) {
        if(car.getId() == null) {
            addCar(car);
        } else {
            editCar(car);
        }
    }

    private void addCar(Car car) {
        carRepository.save(car);
    }

    private void editCar(final Car car) {
        if(!carRepository.existsById(car.getId())) {
            throw new NotFoundException(CAR_NOT_FOUND_ERROR_MESSAGE.formatted(car.getId()));
        }
        carRepository.save(car);
    }

    public void deleteCar(final Integer carId) {
        carRepository.deleteById(carId);
    }

    public Page<Car> findCars(final String manufacturer, final String model, final TimePeriod timePeriod, final Pageable pageable) {
        var cars = carRepository.findCars(manufacturer, model);
        var availableCars = cars.stream()
                .filter(car -> availabilityService.isCarAvailable(car.getId(), timePeriod))
                .collect(Collectors.toList());
        return pageOf(availableCars, pageable);
    }

    public Page<Car> pageOf(final List<Car> cars, final Pageable pageable) {
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), cars.size());
        return new PageImpl<>(cars.subList(start, end), pageable, cars.size());
    }
}
