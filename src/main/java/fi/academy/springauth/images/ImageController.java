package fi.academy.springauth.images;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("api/images")
public class ImageController {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ImageService imageService;


    @PostMapping("")
    @Transactional
    public ResponseEntity<?> addImage(@RequestBody MultipartFile image) throws IOException {

        ImageEntity newImage = imageService.createImage(image);
        imageRepository.save(new ImageEntity(image.getOriginalFilename()));

        URI location = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/images/{id}")
                .buildAndExpand(newImage.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}
