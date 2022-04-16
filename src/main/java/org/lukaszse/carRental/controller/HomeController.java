package org.lukaszse.carRental.controller;

import org.lukaszse.carRental.model.dto.UserDto;
import org.lukaszse.carRental.util.AttributeNames;
import org.lukaszse.carRental.util.Mappings;
import org.lukaszse.carRental.util.ViewNames;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping(Mappings.HOME)
    public String goHome(final Principal principal, final Model model) {
        var user = UserDto.of(principal.getName());
        model.addAttribute(AttributeNames.USER, user);
        return ViewNames.HOME;
    }
}
