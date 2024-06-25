package com.Dealer.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        // Add exception details to the model
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("exceptionType", e.getClass().getSimpleName());
        return "errorPage";
    }
}

