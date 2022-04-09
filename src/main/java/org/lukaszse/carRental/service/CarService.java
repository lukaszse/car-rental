package org.lukaszse.carRental.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lukaszse.carRental.exceptions.NotFoundException;
import org.lukaszse.carRental.model.Car;
import org.lukaszse.carRental.repository.CarRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarService {

    public static final String CAR_NOT_FOUND_ERROR_MESSAGE = "Car with registrationNo=%s has not been found.";
    private final CarRepository repository;


    public List<Car> findAll() {
        return repository.findAll();
    }

    public Car getContractor(Integer id) {
            return repository.findById(id)
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
        repository.save(car);
    }

    public void editContractor(Car car) {
        if(!repository.existsById(car.getId())) {
            throw new NotFoundException(CAR_NOT_FOUND_ERROR_MESSAGE.formatted(car.getId()));
        }
        repository.save(car);
    }

    public void deleteContractor(Integer Contractorid) {
        repository.deleteById(Contractorid);
    }

    public Page<Car> getPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        var users = repository.findAll();
        var contractorsPage = Stream.of(users)
                .filter(orderList -> !orderList.isEmpty())
                .flatMap(Collection::stream)
                .skip((long) currentPage * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
        return new PageImpl<Car>(contractorsPage, PageRequest.of(currentPage, pageSize), users.size());
    }
}
