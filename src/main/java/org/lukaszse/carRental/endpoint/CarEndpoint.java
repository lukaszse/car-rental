package org.lukaszse.carRental.endpoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lukaszse.carRental.model.Car;
import org.lukaszse.carRental.model.TimePeriod;
import org.lukaszse.carRental.service.CarService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarEndpoint {

    private final CarService carService;

    @GetMapping("/findCars")
    public ResponseEntity<Page<Car>> findCars(@RequestParam final String manufacturer,
                                              @RequestParam final String model,
                                              @Nullable final TimePeriod timePeriod,
                                              @RequestParam(name = "pageNumber", defaultValue = "1") final int pageNumber,
                                              @RequestParam(name = "pageSize", defaultValue = "5") final int pageSize) {

        var cars = carService
                .findCars(manufacturer, model, timePeriod, PageRequest.of(pageNumber - 1, pageSize, Sort.by("id")));
        return ResponseEntity.ok(cars);
    }
}
