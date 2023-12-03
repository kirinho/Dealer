package com.Dealer.controllers;

import com.Dealer.entities.Brand;
import com.Dealer.entities.Car;
import com.Dealer.services.BrandService;
import com.Dealer.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CarController {
    @Autowired
    private CarService carService;

    @Autowired
    private BrandService brandService;

    @GetMapping("/cars")
    public String getAllCars(Model model, @RequestParam(name = "filterBrand", required = false) Long selectedBrand) {
        List<Car> cars;
        List<String> base64Photos;

        if (selectedBrand != null) {
            cars = carService.getCarsByBrand(selectedBrand);
            base64Photos = cars.stream()
                    .map(car -> Base64.getEncoder().encodeToString(car.getPhotos().get(0)))
                    .collect(Collectors.toList());
        } else {
            cars = carService.getAllCars();
            base64Photos = cars.stream()
                    .map(car -> Base64.getEncoder().encodeToString(car.getPhotos().get(0)))
                    .collect(Collectors.toList());
        }

        List<Brand> brands = brandService.getAllBrands();
        model.addAttribute("brands", brands);
        model.addAttribute("selectedBrand", selectedBrand);
        model.addAttribute("cars", cars);
        model.addAttribute("base64", base64Photos);
        return "cars";
    }


    @GetMapping("viewCars/{brandId}")
    public String viewCars(Model model, @PathVariable Long brandId){
        List<Car> cars = carService.getCarsByBrand(brandId);
        List<String> base64Photos = new ArrayList<>();
        for (Car car : cars) {
            for (byte[] photo : car.getPhotos()) {
                String base64Photo = Base64.getEncoder().encodeToString(photo);
                base64Photos.add(base64Photo);
                break;
            }
        }
        model.addAttribute("cars", cars);
        model.addAttribute("base64", base64Photos);
        return "viewCars";
    }
    @PostMapping("/deleteCar/{carId}")
    public String deleteCar(@PathVariable Long carId){
        try {
            carService.deleteCar(carId);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return "redirect:/addBrand";
    }

    @GetMapping("/carDetails/{carId}")
    public String getCarDetails(Model model,
                                @PathVariable Long carId){
        Car car = carService.getCarById(carId);
        if(car != null){
            List<String> base64Photos = new ArrayList<>();

            for (byte[] photo : car.getPhotos()) {
                String base64Photo = Base64.getEncoder().encodeToString(photo);
                base64Photos.add(base64Photo);
            }
            model.addAttribute("car", car);
            model.addAttribute("base64", base64Photos);
            return "carDetails";
        }
        else{
            return "redirect:/";
        }

    }


}
