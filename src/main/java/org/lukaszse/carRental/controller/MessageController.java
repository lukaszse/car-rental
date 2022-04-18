package org.lukaszse.carRental.controller;

import lombok.AllArgsConstructor;
import org.lukaszse.carRental.service.MessageService;
import org.lukaszse.carRental.util.AttributeNames;
import org.lukaszse.carRental.util.Mappings;
import org.lukaszse.carRental.util.ViewNames;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class MessageController {


    private final MessageService messageService;

    @GetMapping(Mappings.MESSAGES)
    public String getMessages(@RequestParam(name = "pageNumber", defaultValue = "0") final int pageNumber,
                              @RequestParam(name = "pageSize", defaultValue = "5") final int pageSize,
                              final Model model) {
        var messages = messageService.findMessages(pageNumber, pageSize);
        model.addAttribute(AttributeNames.MESSAGES, messages);
        model.addAttribute(AttributeNames.PAGE_NUMBERS, messageService.getPageNumbers(messages));
        return ViewNames.MESSAGES;
    }
}
