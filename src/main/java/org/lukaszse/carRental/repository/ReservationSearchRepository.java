package org.lukaszse.carRental.repository;

import com.sun.istack.NotNull;
import org.lukaszse.carRental.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationSearchRepository extends PagingAndSortingRepository<Reservation, Integer> {

    Page<Reservation> findByUser_UserNameContainsIgnoreCase(final String userName, final Pageable pageable);

    List<Reservation> findByCar_Id(final Integer carId);
}
