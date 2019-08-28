package fi.academy.springauth.content;


import fi.academy.springauth.appUser.AppUserEntity;
import fi.academy.springauth.appUser.AppUserRepository;
import fi.academy.springauth.aws.AwsRekognitionService;
import fi.academy.springauth.images.metadata.MetadataService;
import fi.academy.springauth.utils.ContentImageService;
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

    @Autowired
    MetadataService metadataService;

    @GetMapping("")
    public Iterable<ContentEntity> testContent() {
        return contentRepository.findAll();
    }

    @PostMapping("")
    @Transactional
    public ResponseEntity<?> addImage(@RequestBody MultipartFile image,
                                      @RequestParam String content,
                                      Principal principal) throws IOException {

        ContentImageEntity newImage = contentImageService.createContentImage(image);
        AppUserEntity creator = appUserRepository.findByUsername(principal.getName()).get();
        boolean featured = System.currentTimeMillis() % 5 == 0 ? true : false;
        ContentEntity newContent = new ContentEntity(content, newImage, creator, featured);

//        newImage.setTags(awsRekognitionService.detectUploadedLabelsResult(image));

        newImage.setTags(awsRekognitionService.detectS3ImageLabelsResults(newImage.getUrl()));
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id, Principal user) {
        System.out.println(user.getName());
        return contentImageService.deleteContentImage(id, user);
    }
}
