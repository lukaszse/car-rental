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

@Slf4j
@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class OrderEndpoint {

    private final ReservationService ordersService;

    @GetMapping("/findReservations")
    public ResponseEntity<Page<Reservation>> findOrders(@RequestParam final String orderName,
                                                        @RequestParam final String contractor,
                                                        @RequestParam(name = "pageNumber", defaultValue = "1") final int pageNumber,
                                                        @RequestParam(name = "pageSize", defaultValue = "5") final int pageSize) {

        var orders = ordersService.findOrders(orderName, contractor, PageRequest.of(pageNumber - 1, pageSize, Sort.by("id")));
        return ResponseEntity.ok(orders);
    }
}
