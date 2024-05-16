package com.Dealer.controllers;
import com.Dealer.repositories.ImageRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    private final ImageRepository imageService;

    public ImageController(ImageRepository imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/images/{carId}/{photoIndex}")
    @ResponseBody
    public ResponseEntity<byte[]> getImage(@PathVariable Long carId, @PathVariable int photoIndex) {
        byte[] imageData = imageService.getImageDataById(carId, photoIndex);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }
}
