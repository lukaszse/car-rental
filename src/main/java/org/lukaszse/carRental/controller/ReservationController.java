package org.lukaszse.carRental.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lukaszse.carRental.model.TimePeriod;
import org.lukaszse.carRental.model.dto.ReservationDto;
import org.lukaszse.carRental.service.CarService;
import org.lukaszse.carRental.service.ReservationService;
import org.lukaszse.carRental.service.UserService;
import org.lukaszse.carRental.util.AttributeNames;
import org.lukaszse.carRental.util.Mappings;
import org.lukaszse.carRental.util.ViewNames;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final CarService carService;
    private final UserService userService;


    @GetMapping(Mappings.RESERVATIONS)
    public String reservations(@RequestParam(name = "pageNumber", defaultValue = "1") final int pageNumber,
                               @RequestParam(name = "pageSize", defaultValue = "5") final int pageSize) {
        // supported by ajax separate call to ReservationEndpoint
        return ViewNames.RESERVATIONS;
    }

    @GetMapping(Mappings.RESERVATION)
    public String reservation(@RequestParam final Integer id, final Model model) {
        var reservationViewDto = ReservationDto.of(reservationService.getReservation(id));
        model.addAttribute(AttributeNames.RESERVATION, reservationViewDto);
        return ViewNames.RESERVATION;
    }

    @GetMapping(Mappings.ADD_RESERVATION)
    public String addReservation(@RequestParam final String userName,
                                  @RequestParam final int carId,
                                  final TimePeriod timePeriod,
                                  final Model model) {
        final ReservationDto reservationViedDto = reservationService.prepareReservationViewDto(userName, carId, timePeriod);
        model.addAttribute(AttributeNames.RESERVATION, reservationViedDto);
        return ViewNames.ADD_RESERVATION;
    }

    @GetMapping(Mappings.EDIT_RESERVATION)
    public String editReservation(@RequestParam final Integer id, final Model model) {
        var reservationViewDto = ReservationDto.of(reservationService.getReservation(id));
        model.addAttribute(AttributeNames.RESERVATION, reservationViewDto);
        model.addAttribute(AttributeNames.CARS, carService.findAll());
        model.addAttribute(AttributeNames.USERS, userService.findAll());
        return ViewNames.EDIT_RESERVATION;
    }

    @PostMapping(Mappings.ADD_RESERVATION)
    public String processAddReservation(@ModelAttribute(AttributeNames.RESERVATION) @Valid final ReservationDto submittedReservation,
                                         final BindingResult bindingResult, final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(AttributeNames.RESERVATION, submittedReservation);
            return ViewNames.ADD_RESERVATION;
        }
        final boolean reservationAdded = reservationService.addReservation(submittedReservation);
        if(reservationAdded) {
            model.addAttribute(AttributeNames.SUCCESS_MESSAGE, "Car is booked!");
        } else {
            model.addAttribute(AttributeNames.FAIL_MESSAGE, "We're sorry, but someone just booked the car of your choice for the date you chose. Please select a different car.");
        }
        return ViewNames.CARS;
    }

    @PostMapping(Mappings.EDIT_RESERVATION)
    public String processEditReservation(@ModelAttribute(AttributeNames.RESERVATION) @Valid final ReservationDto submittedReservation,
                                         final BindingResult bindingResult, final Model model) {
        if (!bindingResult.hasErrors()) {
            reservationService.updateReservation(submittedReservation);
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
