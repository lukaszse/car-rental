package org.lukaszse.carRental.repository;

import org.lukaszse.carRental.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CarSearchRepository extends PagingAndSortingRepository<Car, Integer> {

    Page<Car> findCarByManufacturerContainsIgnoreCaseAndModelContainsIgnoreCase(final String manufacturer, final String model, final Pageable pageable);
}
