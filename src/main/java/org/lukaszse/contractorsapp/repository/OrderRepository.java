package org.lukaszse.contractorsapp.repository;

import org.lukaszse.contractorsapp.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Reservation, Integer> {
}
