package org.lukaszse.carRental.service;

import lombok.AllArgsConstructor;
import org.lukaszse.carRental.model.Message;
import org.lukaszse.carRental.repository.MessageSearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageSearchRepository messageSearchRepository;

    public Page<Message> findMessages(final Integer pageNumber, final Integer pageSize) {
        final Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id"));
        return messageSearchRepository.findAll(pageable);
    }

    public void sendMessage(final Message message, final Principal principal) {
        final LocalDate sentDate = message.getSentDate() != null ? message.getSentDate() : LocalDate.now();
        final String userName = message.getUserName() != null ? message.getUserName() : principal.getName();
        final Message newMessage = Message.of(message, userName, sentDate);
        messageSearchRepository.save(newMessage);
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
