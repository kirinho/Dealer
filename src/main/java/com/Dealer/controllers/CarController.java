package com.Dealer.controllers;

import com.Dealer.entities.Brand;
import com.Dealer.entities.Car;
import com.Dealer.services.BrandService;
import com.Dealer.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

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
        return isAdmin ? "carsAdmin" : "cars";
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
    @GetMapping("/updateCar/{carId}")
    public String showUpdateCarForm(@PathVariable Long carId, Model model) {
        Car car = carService.getCarById(carId);
        model.addAttribute("item", car);
        return "updateCar";
    }

    @PostMapping("/updateCar/{carId}")
    public String updateCar(RedirectAttributes redirectAttributes,
                            @RequestParam Long carId,
                            @RequestParam Long brandId,
                            @RequestParam String Model,
                            @RequestParam String City,
                            @RequestParam int Year,
                            @RequestParam int Mileage,
                            @RequestParam String Type,
                            @RequestParam String Colour,
                            @RequestParam String TypeEngine,
                            @RequestParam double SizeEngine,
                            @RequestParam int PowerEngine,
                            @RequestParam String Box,
                            @RequestParam String TypeDrive,
                            @RequestParam int Price,
                            @RequestParam String Description,
                            @RequestParam("upload-input") MultipartFile[] carPhotos) throws IOException {
        var car = carService.getCarById(carId);
        var brand = brandService.getBrandById(brandId);
        car.setModel(Model);
        car.setCity(City);
        car.setYear(Year);
        car.setMileage(Mileage);
        car.setType(Type);
        car.setColour(Colour);
        car.setTypeEngine(TypeEngine);
        car.setSizeEngine(SizeEngine);
        car.setPowerEngine(PowerEngine);
        car.setBox(Box);
        car.setTypeDrive(TypeDrive);
        car.setPrice(Price);
        car.setDescription(Description);
        car.setBrand(brand);
        if (carPhotos != null && carPhotos.length > 1) {
            List<byte[]> photoList = new ArrayList<>();
            for (MultipartFile photo : carPhotos) {
                photoList.add(photo.getBytes());
            }
            car.setPhotos(photoList);
        }
        brand.getCarList().add(car);
        brandService.saveBrand(brand);
        return "redirect:/viewCars/" + brandId;
    }
}
