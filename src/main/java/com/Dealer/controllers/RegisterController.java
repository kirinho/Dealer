package com.Dealer.controllers;

import com.Dealer.entities.Role;
import com.Dealer.entities.Status;
import com.Dealer.entities.User;
import com.Dealer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("register")
    public String registration(Model model, String username, String password){
        User existingUser = userRepository.findByUsername(username);
        if(existingUser != null){
            model.addAttribute("errorMessage", "З таким логіном користувач вже існує!");
            return "register";
        }
        else {
            User newUser = new User();
            newUser.setUsername(username);
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
            newUser.setRole(Role.USER);
            newUser.setStatus(Status.ACTIVE);
            userRepository.save(newUser);
            Authentication authentication = new UsernamePasswordAuthenticationToken(newUser.getUsername(), password);
            authentication = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/cars";
        }
    }
}