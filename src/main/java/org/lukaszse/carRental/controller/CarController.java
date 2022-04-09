package org.lukaszse.carRental.controller;

import lombok.extern.slf4j.Slf4j;
import org.lukaszse.carRental.model.Car;
import org.lukaszse.carRental.service.CarService;
import org.lukaszse.carRental.util.AttributeNames;
import org.lukaszse.carRental.util.Mappings;
import org.lukaszse.carRental.util.ViewNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
@Controller
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }


    @GetMapping(Mappings.MANAGEMENT_CARS)
    public String cars(@RequestParam(name = "pageNumber", defaultValue = "1") final int pageNumber,
                       @RequestParam(name = "pageSize", defaultValue = "5") final int pageSize,
                       final Model model) {
        Page<Car> usersPage = carService.getPaginated(PageRequest.of(pageNumber - 1, pageSize));
        model.addAttribute(AttributeNames.CAR_PAGE, usersPage);
        Stream.of(usersPage.getTotalPages())
                .filter(totalPages -> totalPages > 0)
                .map(totalPages -> IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList()))
                .forEach(pageNumbers -> model.addAttribute(AttributeNames.PAGE_NUMBERS, pageNumbers));
        return ViewNames.MANAGEMENT_CARS;
    }

    @GetMapping(Mappings.MANAGEMENT_CAR)
    public String car(@RequestParam Integer id, Model model) {
        model.addAttribute(AttributeNames.CAR, carService.getContractor(id));
        return ViewNames.MANAGEMENT_CAR;
    }

    @GetMapping(Mappings.ADD_CAR)
    public String addCar(Model model) {
        model.addAttribute(AttributeNames.CAR, new Car());
        return ViewNames.ADD_CAR;
    }

    @GetMapping(Mappings.EDIT_CAR)
    public String addCar(@RequestParam Integer id, Model model) {
        model.addAttribute(AttributeNames.CAR, carService.getContractor(id));
        return ViewNames.ADD_CAR;
    }

    @Transactional
    @PostMapping(Mappings.ADD_CAR)
    public String processAddEditCar(
            @ModelAttribute(AttributeNames.CAR) @Valid Car car,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ViewNames.ADD_CAR;
        }
        carService.addEditContractor(car);
        return "redirect:/" + Mappings.MANAGEMENT_CARS;
    }

    @GetMapping(Mappings.DELETE_CAR)
    public String deleteContractor(@RequestParam Integer id) {
        carService.deleteContractor(id);
        return "redirect:/" + Mappings.MANAGEMENT_CARS;
    }
}
