package com.Dealer.services;

public interface ImageService {
    byte[] getImageDataById(Long carId, int photoIndex);
}
