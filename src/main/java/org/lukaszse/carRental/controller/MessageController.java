package org.lukaszse.carRental.controller;

import lombok.AllArgsConstructor;
import org.lukaszse.carRental.model.Message;
import org.lukaszse.carRental.service.MessageService;
import org.lukaszse.carRental.service.ReCaptchaValidationService;
import org.lukaszse.carRental.util.AttributeNames;
import org.lukaszse.carRental.util.Mappings;
import org.lukaszse.carRental.util.ViewNames;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private ReCaptchaValidationService reCaptchaValidationService;

    @GetMapping(Mappings.MESSAGES)
    public String getMessages(@RequestParam(name = "pageNumber", defaultValue = "0") final int pageNumber,
                              @RequestParam(name = "pageSize", defaultValue = "5") final int pageSize,
                              final Model model) {
        var messages = messageService.findMessages(pageNumber, pageSize);
        model.addAttribute(AttributeNames.MESSAGES, messages);
        model.addAttribute(AttributeNames.PAGE_NUMBERS, MessageService.getPageNumbers(messages));
        return ViewNames.MESSAGES;
    }

    @GetMapping(Mappings.SEND_MESSAGE)
    public String sendMessage(final Model model) {
        model.addAttribute(AttributeNames.MESSAGE, new Message());
        return ViewNames.SEND_MESSAGE;
    }

    @PostMapping(Mappings.SEND_MESSAGE)
    public String performSendMessage(@ModelAttribute(AttributeNames.MESSAGE) @Valid final Message message,
                                     final BindingResult bindingResult, final Model model, final Principal principal,
                                     @Nullable @RequestParam(name = "g-recaptcha-response") final String recaptchaResponse) {

        boolean isUserARobot = isUserARobot(principal, recaptchaResponse);

        if(isUserARobot) {
            model.addAttribute(AttributeNames.FAIL_MESSAGE, "Please Verify Captcha");
        }

        var bindingResultAfterNameValidation = validateUserName(message.getUserName(), principal, bindingResult);
        if (isUserARobot || bindingResultAfterNameValidation.hasErrors()) {
            return ViewNames.SEND_MESSAGE;
        }

        messageService.sendMessage(message, principal);
        model.addAttribute(AttributeNames.SUCCESS_MESSAGE, "Message successfully sent. Do you want to send another?");
        return ViewNames.SEND_MESSAGE;
    }

    @GetMapping(Mappings.DELETE_MESSAGE)
    public String deleteMessage(@RequestParam final Integer id) {
        messageService.deleteMessage(id);
        return "redirect:/" + Mappings.MESSAGES;
    }

    private static BindingResult validateUserName(final String userName, final Principal principal, BindingResult bindingResult) {
        if ((userName == null || userName.isBlank()) && principal == null) {
            var error = new ObjectError("message", "User Name cannot be empty");
            bindingResult.addError(error);
        }
        return bindingResult;
    }

    private boolean isUserARobot(final Principal principal, final String recaptchaResponse) {
        boolean reCaptchaVerified = reCaptchaValidationService.validateCaptcha(recaptchaResponse);
        return !reCaptchaVerified && principal == null;
    }
}
