package fi.academy.springauth.content;

import fi.academy.springauth.appUser.AppUserEntity;
import fi.academy.springauth.appUser.AppUserRepository;
import fi.academy.springauth.images.ImageEntity;
import fi.academy.springauth.images.ImageRepository;
import fi.academy.springauth.images.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("/content")
public class ContentController {

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ImageService imageService;

    @Autowired
    AppUserRepository appUserRepository;

//    @GetMapping("")
//    public Iterable<ContentEntity> testContent() {
//        return contentRepository.findAll();
//    }
//    @PostMapping("")
//    @Transactional
//    public ResponseEntity<?> addImage(@RequestBody MultipartFile image,
//                                      @RequestParam String content,
//                                      Principal principal) throws IOException {
//        System.out.println(content);
//        System.out.println(image);
//        System.out.println(principal.getName());
//
//        ImageEntity newImage = imageService.createImage(image);
//        AppUserEntity creator = appUserRepository.findByUsername(principal.getName());
//        ContentEntity newContent = new ContentEntity(content, newImage, creator);
//        contentRepository.save(newContent);
//
//        URI location = UriComponentsBuilder.newInstance()
//                .scheme("http")
//                .host("localhost")
//                .port(8080)
//                .path("/image/{id}")
//                .buildAndExpand(newImage.getId())
//                .toUri();
//        return ResponseEntity.created(location).build();
//    }
}
