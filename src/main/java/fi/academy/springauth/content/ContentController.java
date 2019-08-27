package fi.academy.springauth.content;


import fi.academy.springauth.appUser.AppUserEntity;
import fi.academy.springauth.appUser.AppUserRepository;
import fi.academy.springauth.aws.AwsRekognitionService;
import fi.academy.springauth.images.ImageEntity;
import fi.academy.springauth.images.ImageRepository;

import fi.academy.springauth.utils.ContentImageService;
import fi.academy.springauth.utils.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.Date;

@RestController
@RequestMapping("/api/content")
public class ContentController {

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    ContentImageRepository contentImageRepository;

    @Autowired
    ContentImageService contentImageService;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    AwsRekognitionService awsRekognitionService;

    @GetMapping("")
    public Iterable<ContentEntity> testContent() {
        return contentRepository.findAll();
    }

    @PostMapping("")
    @Transactional
    public ResponseEntity<?> addImage(@RequestBody MultipartFile image,
                                      @RequestParam String content,
                                      Principal principal) throws IOException {
        System.out.println(content);
        System.out.println(image);
        System.out.println(principal.getName());
        System.out.println(awsRekognitionService.detectUploadedLabelsResult(image));

        ContentImageEntity newImage = contentImageService.createImage(image);
        AppUserEntity creator = appUserRepository.findByUsername(principal.getName()).get();
        boolean featured = System.currentTimeMillis() % 5 == 0 ? true : false;
        ContentEntity newContent = new ContentEntity(content, newImage, creator, featured);
//        newContent.setTags(awsRekognitionService.detectUploadedLabelsResult(imag));
//        newImage.setTags(awsRekognitionService.detectUploadedLabelsResult(image));
        contentRepository.save(newContent);
        newImage.setContent(newContent);
        contentImageRepository.save(newImage);

        URI location = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/image/{id}")
                .buildAndExpand(4)
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
