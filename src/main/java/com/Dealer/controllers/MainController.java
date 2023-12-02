package com.Dealer.controllers;

import com.Dealer.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/")
    public String greeting(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && !"anonymousUser".equals(authentication.getPrincipal());
        if(isAuthenticated){
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
            if(isAdmin){
                return "greetingAdmin";
            }
            else if(!isAdmin){
                return "greetingUser";
            }
        }
        return "greeting";
    }

    @GetMapping("login")
    public String login(){
        return "login";
    }

    @GetMapping("register")
    public String register(){return "register";}

//    @GetMapping("cars")
//    public String cars(){
//        return "cars";
//    }
}
