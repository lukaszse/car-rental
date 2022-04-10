package org.lukaszse.carRental.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lukaszse.carRental.model.Reservation;
import org.lukaszse.carRental.model.dto.ReservationDto;
import org.lukaszse.carRental.model.dto.ReservationViewDto;
import org.lukaszse.carRental.service.CarService;
import org.lukaszse.carRental.service.ReservationService;
import org.lukaszse.carRental.service.SettingService;
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
    private final SettingService settingService;


    @GetMapping(Mappings.ADD_RESERVATION)
    public String addOrderView(final Model model) {
        model.addAttribute(AttributeNames.RESERVATION, new ReservationViewDto());
        model.addAttribute(AttributeNames.CARS, carService.findAll());
        return ViewNames.ADD_RESERVATION;
    }

    @GetMapping(Mappings.EDIT_RESERVATION)
    public String editOrderView(@RequestParam final Integer id, Model model) {
        var reservation = reservationService.getReservation(id);
        var reservationReader = ReservationViewDto.of(reservation);
        model.addAttribute(AttributeNames.RESERVATION, reservationReader);
        model.addAttribute(AttributeNames.CARS, carService.findAll());
        return ViewNames.ADD_RESERVATION;
    }

    @GetMapping(Mappings.RESERVATIONS)
    public String orderListView(@RequestParam(name = "pageNumber", defaultValue = "1") final int pageNumber,
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

    @PostMapping(Mappings.ADD_RESERVATION)
    public String addOrder(
            @ModelAttribute(AttributeNames.RESERVATION) @Valid final ReservationDto submittedOrder,
            final BindingResult bindingResult, final Model model) {
        if (!bindingResult.hasErrors()) {
            reservationService.addEditReservation(submittedOrder);
            return "redirect:/" + Mappings.RESERVATIONS;
        }
        model.addAttribute(Map.of(
                AttributeNames.RESERVATION, submittedOrder,
                AttributeNames.CARS, carService.findAll()));
        return ViewNames.ADD_RESERVATION;
    }

    @GetMapping(Mappings.DELETE_RESERVATION)
    public String deleteOrder(@RequestParam final Integer id) {
        reservationService.deleteReservation(id);
        return "redirect:/" + Mappings.RESERVATIONS;
    }

    @GetMapping(Mappings.VIEW_ORDER)
    public String orderView(@RequestParam final Integer id, final Model model) {
        var reservationReader = ReservationViewDto.of(reservationService.getReservation(id));
        model.addAllAttributes(Map.of(
                AttributeNames.RESERVATION, reservationReader,
                "settingsSet", settingService.getCurrentSettings()));
        return ViewNames.RESERVATION;
    }
}
