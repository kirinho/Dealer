package com.Dealer.controllers;

import com.Dealer.entities.Car;
import com.Dealer.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping("/cars")
    public String getAllCars(Model model) {
        List<Car> cars = carService.getAllCars();

        List<String> base64Photos = new ArrayList<>();
        for (Car car : cars) {
            for (byte[] photo : car.getPhotos()) {
                String base64Photo = Base64.getEncoder().encodeToString(photo);
                base64Photos.add(base64Photo);
            }
        }
        model.addAttribute("cars", cars);
        model.addAttribute("base64", base64Photos);
        return "cars";
    }


}
