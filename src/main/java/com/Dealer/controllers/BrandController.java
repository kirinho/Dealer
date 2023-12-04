package com.Dealer.controllers;

import com.Dealer.entities.Brand;
import com.Dealer.entities.Car;
import com.Dealer.entities.User;
import com.Dealer.services.BrandService;
import com.Dealer.services.CarService;
import com.Dealer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BrandController {
    @Autowired
    private UserService userService;

    @Autowired
    private BrandService brandService;


    @GetMapping("addBrand")
    public String addBrand(Model model, Authentication authentication){
        List<Brand> brands = brandService.getAllBrands();
        model.addAttribute("brands", brands);
        return "addBrand";
    }

    @PostMapping("/createBrand")
    public String createBrand(@RequestParam("brandName") String brandName, Authentication authentication){
        User user = userService.getUserFromAuthentication(authentication);
        Brand brand = new Brand();
        brand.setName(brandName);
        brand.setUser(user);
        brandService.saveBrand(brand);
        return "redirect:/addBrand";
    }

    @GetMapping("addCar/{brandId}")
    public String editBrand(@PathVariable Long brandId, Model model, Authentication authentication){
        Brand brand = brandService.getBrandById(brandId);
        model.addAttribute("brand", brand);
        return "addCar";
    }


    @GetMapping("/deleteBrand/{brandId}")
    public String deleteBrand(@PathVariable Long brandId){
        try {
            brandService.deleteBrand(brandId);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return "redirect:/addBrand";
    }

    @PostMapping("/saveCar")
    public String saveCar(RedirectAttributes redirectAttributes,
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

        Brand brand = brandService.getBrandById(brandId);
        Car car = new Car();
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
        List<byte[]> photoList = new ArrayList<>();

        for (MultipartFile photo : carPhotos) {
            photoList.add(photo.getBytes());
        }

        car.setPhotos(photoList);

        brand.getCarList().add(car);
        brandService.saveBrand(brand);
        return "redirect:/addCar/" + brandId;
    }

}
