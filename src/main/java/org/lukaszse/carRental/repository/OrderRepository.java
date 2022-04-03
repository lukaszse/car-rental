package org.lukaszse.carRental.repository;

import org.lukaszse.carRental.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Reservation, Integer> {
}
