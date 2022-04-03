package org.lukaszse.contractorsapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lukaszse.contractorsapp.model.Order;
import org.lukaszse.contractorsapp.model.dto.OrderDto;
import org.lukaszse.contractorsapp.model.dto.OrderViewDto;
import org.lukaszse.contractorsapp.service.CarService;
import org.lukaszse.contractorsapp.service.ReservationService;
import org.lukaszse.contractorsapp.service.SettingService;
import org.lukaszse.contractorsapp.util.AttributeNames;
import org.lukaszse.contractorsapp.util.Mappings;
import org.lukaszse.contractorsapp.util.ViewNames;
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
        model.addAttribute(AttributeNames.RESERVATION, new OrderViewDto());
        model.addAttribute(AttributeNames.CARS, carService.findAll());
        return ViewNames.ADD_RESERVATION;
    }

    @GetMapping(Mappings.EDIT_RESERVATION)
    public String editOrderView(@RequestParam final Integer id, Model model) {
        var order = reservationService.getOrder(id);
        var orderReader = new OrderViewDto(order);
        model.addAttribute(AttributeNames.RESERVATION, orderReader);
        model.addAttribute(AttributeNames.CARS, carService.findAll());
        return ViewNames.ADD_RESERVATION;
    }

    @GetMapping(Mappings.RESERVATIONS)
    public String orderListView(@RequestParam(name = "pageNumber", defaultValue = "1") final int pageNumber,
                                @RequestParam(name = "pageSize", defaultValue = "5") final int pageSize,
                                final Model model) {
        Page<Order> orderPage = reservationService.getPaginated(PageRequest.of(pageNumber - 1, pageSize));
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
            @ModelAttribute(AttributeNames.RESERVATION) @Valid final OrderDto submittedOrder,
            final BindingResult bindingResult, final Model model) {
        if (!bindingResult.hasErrors()) {
            reservationService.addEditOrder(submittedOrder);
            return "redirect:/" + Mappings.RESERVATIONS;
        }
        model.addAttribute(Map.of(
                AttributeNames.RESERVATION, submittedOrder,
                AttributeNames.CARS, carService.findAll()));
        return ViewNames.ADD_RESERVATION;
    }

    @GetMapping(Mappings.DELETE_RESERVATION)
    public String deleteOrder(@RequestParam final Integer id) {
        reservationService.deleteOrder(id);
        return "redirect:/" + Mappings.RESERVATIONS;
    }

    @GetMapping(Mappings.VIEW_ORDER)
    public String orderView(@RequestParam final Integer id, final Model model) {
        var orderReader = new OrderViewDto(reservationService.getOrder(id));
        model.addAllAttributes(Map.of(
                AttributeNames.RESERVATION, orderReader,
                "settingsSet", settingService.getCurrentSettings()));
        log.info("Order description: " + orderReader.getOrderDescription() + " price after processing " + orderReader.getPrice());
        return ViewNames.RESERVATION;
    }
}
