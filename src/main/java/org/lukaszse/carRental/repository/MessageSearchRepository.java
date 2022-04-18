package org.lukaszse.carRental.repository;

import org.lukaszse.carRental.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageSearchRepository extends PagingAndSortingRepository<Message, Integer> {

    Page<Message> findByUserNameContainingIgnoreCase(final String userName, final Pageable pageable);
}