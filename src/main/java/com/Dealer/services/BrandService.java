package com.Dealer.services;

import com.Dealer.entities.Brand;
import com.Dealer.repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;

    public Brand getBrandById(Long brandId){
        return brandRepository.findById(brandId).orElse(null);
    }
    public Brand saveBrand(Brand brand){
        return brandRepository.save(brand);
    }
    public List<Brand> getAllBrands(){
        return brandRepository.findAll();
    }
    public void deleteBrand(Long brandId){
        brandRepository.deleteById(brandId);
    }
}
