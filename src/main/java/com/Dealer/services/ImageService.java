package com.Dealer.services;

import com.Dealer.entities.Car;
import com.Dealer.repositories.CarRepository;
import com.Dealer.repositories.ImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService implements ImageRepository {

    private final CarRepository carRepository;

    public ImageService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public byte[] getImageDataById(Long carId, int photoIndex) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        List<byte[]> photos = car.getPhotos();

        if (photoIndex >= 0 && photoIndex < photos.size()) {
            return photos.get(photoIndex);
        } else {
            throw new RuntimeException("Photo not found");
        }
    }
}

