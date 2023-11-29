package com.Dealer.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String greeting(Model model, Authentication authentication){
        return "greeting";
    }

    @GetMapping("login")
    public String login(){
        return "login";
    }
}
