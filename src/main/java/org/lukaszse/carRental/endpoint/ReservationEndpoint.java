package org.lukaszse.carRental.endpoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lukaszse.carRental.model.Reservation;
import org.lukaszse.carRental.service.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationEndpoint {

    private final ReservationService reservationService;

    @GetMapping("/findReservations")
    public ResponseEntity<Page<Reservation>> findReservations(@RequestParam final String userName,
                                                              @RequestParam(name = "pageNumber", defaultValue = "1") final int pageNumber,
                                                              @RequestParam(name = "pageSize", defaultValue = "5") final int pageSize) {

        var reservationsPage = reservationService
                .findReservations(userName, PageRequest.of(pageNumber - 1, pageSize, Sort.by("id")));
        return ResponseEntity.ok(reservationsPage);
    }

    @GetMapping("/findReservationsForLoggedUser")
    public ResponseEntity<Page<Reservation>> findReservations(@RequestParam(name = "pageNumber", defaultValue = "1") final int pageNumber,
                                                              @RequestParam(name = "pageSize", defaultValue = "5") final int pageSize,
                                                              final Principal principal) {
        var reservationsPage = reservationService
                .findReservations(principal.getName(), PageRequest.of(pageNumber - 1, pageSize, Sort.by("id")));
        return ResponseEntity.ok(reservationsPage);
    }
}
