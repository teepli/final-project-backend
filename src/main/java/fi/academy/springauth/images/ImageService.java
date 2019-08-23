package fi.academy.springauth.images;

import fi.academy.springauth.appUser.AppUserRepository;
import fi.academy.springauth.photoShoot.PhotoshootPlanEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Optional;

@Service
public class ImageService {


    @Value(value = "${UPLOAD_ROOT}")
    private String UPLOAD_ROOT;

    private final ImageRepository imageRepository;
    private final ResourceLoader resourceLoader;

    @Autowired
    public ImageService(ImageRepository imageRepository, ResourceLoader resourceLoader, AppUserRepository appUserRepository) {
        this.imageRepository = imageRepository;
        this.resourceLoader = resourceLoader;

    }

    public ImageEntity createImage(MultipartFile file) throws IOException {
        ImageEntity created = null;
        long time = System.currentTimeMillis();

        if (!file.isEmpty()) {
            Files.copy(file.getInputStream(), Paths.get(UPLOAD_ROOT, time + file.getOriginalFilename()));
            created = imageRepository.save(new ImageEntity(time + file.getOriginalFilename()));
            // https://github.com/drewnoakes/metadata-extractor
        }
        return created;
    }

    public ResponseEntity<?> deleteImage(long id, Principal user) {
        Optional<ImageEntity> currentImage = Optional.ofNullable(imageRepository.findById(id));
        if (!currentImage.isPresent()){
            return new ResponseEntity<>("Error: there is no picture with chosen id", HttpStatus.NOT_FOUND);
        }
        if (currentImage.get().getPhotoshoot().getCreator().getUsername().equals(user.getName())) {
            imageRepository.delete(currentImage.get());
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>("Not authorized", HttpStatus.BAD_REQUEST);
    }
}
