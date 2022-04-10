package org.lukaszse.carRental.controller;

import org.lukaszse.carRental.util.Mappings;
import org.lukaszse.carRental.util.ViewNames;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(Mappings.HOME)
    public String goHome() {
        return ViewNames.HOME;
    }
}
