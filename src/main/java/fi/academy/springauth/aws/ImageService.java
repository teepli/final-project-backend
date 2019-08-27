package fi.academy.springauth.aws;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import fi.academy.springauth.appUser.AppUserRepository;
import fi.academy.springauth.images.ImageEntity;
import fi.academy.springauth.images.ImageRepository;
import fi.academy.springauth.images.metadata.MetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Optional;

@Service
@Profile("prod")
public class ImageService implements fi.academy.springauth.utils.ImageService {

    @Autowired
    private AmazonS3Client amazonS3Client;

    @Value(value = "${UPLOAD_ROOT}")
    private String UPLOAD_ROOT;

    private final ImageRepository imageRepository;
    private final ResourceLoader resourceLoader;
    private final MetadataService metadataService;

    @Autowired
    public ImageService(ImageRepository imageRepository, ResourceLoader resourceLoader, AppUserRepository appUserRepository, MetadataService metadataService, AmazonS3Client amazonClient) {
        this.imageRepository = imageRepository;
        this.resourceLoader = resourceLoader;
        this.metadataService = metadataService;
        this.amazonS3Client = amazonClient;
    }

    public ImageEntity createImage(MultipartFile file) throws IOException {
        ImageEntity created = null;
        long time = System.currentTimeMillis();

        if (!file.isEmpty()) {
            String newFile = amazonS3Client.uploadFileToS3Bucket(file, false);
            created = imageRepository.save(new ImageEntity(newFile));
            // https://github.com/drewnoakes/metadata-extractor
            readMetadata(file, created, newFile);
        }
        return created;

    }

    private void readMetadata(MultipartFile multipartFile, ImageEntity created, String fileName) {

        try {
            File file = new File(fileName);
            FileOutputStream fos = null;
            fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();
            created.setMetadatalist(metadataService.metadataReader(file));
            file.delete();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

