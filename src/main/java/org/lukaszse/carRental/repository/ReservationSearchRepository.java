package org.lukaszse.carRental.repository;

import com.sun.istack.NotNull;
import org.lukaszse.carRental.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationSearchRepository extends PagingAndSortingRepository<Reservation, Integer> {

    Page<Reservation> findByUser_UserNameContainsIgnoreCaseAndCar_RegistrationNoContainsIgnoreCase(final String userName, final String registrationNo, final Pageable pageable);
}
