package org.lukaszse.contractorsapp.repository;

import org.lukaszse.contractorsapp.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderSearchRepository extends PagingAndSortingRepository<Reservation, Integer> {

    Page<Reservation> findReservationByReservationNameContainsAndCar_NameContainsIgnoreCase(final String orderName, final String contractor, final Pageable pageable);
}
