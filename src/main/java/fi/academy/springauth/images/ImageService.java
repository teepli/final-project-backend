package fi.academy.springauth.images;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class ImageService {


    @Value(value = "${UPLOAD_ROOT}")
    private String UPLOAD_ROOT;

    private final ImageRepository imageRepository;
    private final ResourceLoader resourceLoader;

    @Autowired
    public ImageService(ImageRepository imageRepository, ResourceLoader resourceLoader) {
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
}
