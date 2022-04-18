package org.lukaszse.carRental.service;

import lombok.AllArgsConstructor;
import org.lukaszse.carRental.model.Message;
import org.lukaszse.carRental.repository.MessageSearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageSearchRepository messageRepository;

    public Page<Message> findMessages(final Integer pageNumber, final Integer pageSize) {
        final Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id"));
        return messageRepository.findByUserNameContainingIgnoreCase("user", pageable);
    }

    public List<Integer> getPageNumbers(final Page<Message> messagesPage) {
        return Stream.of(messagesPage.getTotalPages())
                .filter(totalPages -> totalPages > 0)
                .map(totalPages -> IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList()))
                .reduce((first, second) -> second) // get last element of stream
                .orElse(Collections.emptyList());
    }


}
