package org.lukaszse.carRental.controller;

import org.lukaszse.carRental.enums.SecurityRole;
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

    @GetMapping
    public String home() {
        return "redirect:/" + ViewNames.HOME;
    }

    @GetMapping(Mappings.HOME)
    public String goHome(final Principal principal, final Model model) {
        var user = principal != null ? UserDto.of(principal.getName()) : UserDto.of("Guest");
        model.addAttribute(AttributeNames.USER, user);
        return ViewNames.HOME;
    }
}
