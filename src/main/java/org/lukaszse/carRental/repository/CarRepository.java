package org.lukaszse.carRental.repository;

import org.lukaszse.carRental.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    
}
