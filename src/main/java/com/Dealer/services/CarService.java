package com.Dealer.services;

import com.Dealer.entities.Brand;
import com.Dealer.entities.Car;
import com.Dealer.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public Car getCarById(Long carId){
        return carRepository.findById(carId).orElse(null);
    }

    public List<Car> getAllCars(){
        return carRepository.findAll();
    }

    public void deleteCar(Long carId){
        carRepository.deleteById(carId);
    }

    public List<Car> getCarsByBrand(Long brandId){
        return carRepository.findByBrandId(brandId);
    }

}
