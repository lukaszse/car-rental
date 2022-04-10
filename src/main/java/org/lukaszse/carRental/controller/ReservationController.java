package org.lukaszse.carRental.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lukaszse.carRental.model.Reservation;
import org.lukaszse.carRental.model.dto.ReservationDto;
import org.lukaszse.carRental.model.dto.ReservationViewDto;
import org.lukaszse.carRental.service.CarService;
import org.lukaszse.carRental.service.ReservationService;
import org.lukaszse.carRental.service.UserService;
import org.lukaszse.carRental.util.AttributeNames;
import org.lukaszse.carRental.util.Mappings;
import org.lukaszse.carRental.util.ViewNames;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final CarService carService;
    private final UserService userService;


    @GetMapping(Mappings.RESERVATIONS)
    public String ReservationsView(@RequestParam(name = "pageNumber", defaultValue = "1") final int pageNumber,
                                   @RequestParam(name = "pageSize", defaultValue = "5") final int pageSize,
                                   final Model model) {
        Page<Reservation> orderPage = reservationService.getAllReservations(PageRequest.of(pageNumber - 1, pageSize));
        model.addAttribute(AttributeNames.ORDER_PAGE, orderPage);
        Stream.of(orderPage.getTotalPages())
                .filter(totalPages -> totalPages > 0)
                .map(totalPages -> IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList()))
                .forEach(pageNumbers -> model.addAttribute(AttributeNames.PAGE_NUMBERS, pageNumbers));
        return ViewNames.RESERVATIONS;
    }

    @GetMapping(Mappings.RESERVATION)
    public String reservationView(@RequestParam final Integer id, final Model model) {
        var reservationViewDto = ReservationViewDto.of(reservationService.getReservation(id));
        model.addAttribute(AttributeNames.RESERVATION, reservationViewDto);
        return ViewNames.RESERVATION;
    }

    @GetMapping(Mappings.EDIT_RESERVATION)
    public String editReservationView(@RequestParam final Integer id, Model model) {
        var reservationViewDto = ReservationViewDto.of(reservationService.getReservation(id));
        model.addAttribute(AttributeNames.RESERVATION, reservationViewDto);
        model.addAttribute(AttributeNames.CARS, carService.findAll());
        model.addAttribute(AttributeNames.USERS, userService.findAll());
        return ViewNames.EDIT_RESERVATION;
    }

    @PostMapping(Mappings.EDIT_RESERVATION)
    public String editReservation(@ModelAttribute(AttributeNames.RESERVATION) @Valid final ReservationDto submittedReservation,
                                  final BindingResult bindingResult, final Model model) {
        if (!bindingResult.hasErrors()) {
            reservationService.addEditReservation(submittedReservation);
            return "redirect:/" + Mappings.RESERVATIONS;
        }
        model.addAttribute(Map.of(
                AttributeNames.RESERVATION, submittedReservation,
                AttributeNames.CARS, carService.findAll()));
        return ViewNames.EDIT_RESERVATION;
    }

    @GetMapping(Mappings.DELETE_RESERVATION)
    public String deleteReservation(@RequestParam final Integer id) {
        reservationService.deleteReservation(id);
        return "redirect:/" + Mappings.RESERVATIONS;
    }
}
