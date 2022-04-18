package org.lukaszse.carRental.controller;

import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.lukaszse.carRental.model.Car;
import org.lukaszse.carRental.service.CarService;
import org.lukaszse.carRental.util.AttributeNames;
import org.lukaszse.carRental.util.Mappings;
import org.lukaszse.carRental.util.ViewNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@Controller
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }


    @GetMapping(Mappings.CARS)
    public String cars() {
        // supported by ajax separate call to reservation CarEndpoint
        return ViewNames.CARS;
    }

    @GetMapping(Mappings.CAR)
    public String car(@RequestParam final Integer id, final Model model) {
        model.addAttribute(AttributeNames.CAR, carService.getCar(id));
        return ViewNames.CAR;
    }

    @GetMapping(Mappings.ADD_CAR)
    public String addCar(final Model model) {
        model.addAttribute(AttributeNames.CAR, new Car());
        return ViewNames.ADD_CAR;
    }

    @GetMapping(Mappings.EDIT_CAR)
    public String editCar(@RequestParam final Integer id, final Model model) {
        model.addAttribute(AttributeNames.CAR, carService.getCar(id));
        return ViewNames.ADD_CAR;
    }

    @Transactional
    @PostMapping(Mappings.ADD_CAR)
    public String processAddEditCar(@ModelAttribute(AttributeNames.CAR) @Valid final Car car,
                                    final BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ViewNames.ADD_CAR;
        }
        carService.addEditCar(car);
        return "redirect:/" + Mappings.CARS;
    }

    @GetMapping(Mappings.DELETE_CAR)
    public String deleteCar(@RequestParam final Integer id) {
        carService.deleteCar(id);
        return "redirect:/" + Mappings.CARS;
    }
}
