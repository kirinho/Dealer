package com.Dealer.repositories;

public interface ImageRepository {
    byte[] getImageDataById(Long carId, int photoIndex);
}
