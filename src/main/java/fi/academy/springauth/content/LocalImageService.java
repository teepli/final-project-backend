package fi.academy.springauth.content;

import fi.academy.springauth.images.ImageEntity;
import fi.academy.springauth.images.metadata.MetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

@Service
@Profile("dev")
public class LocalImageService implements fi.academy.springauth.utils.ContentImageService {

    @Value(value = "${UPLOAD_ROOT}")
    private String UPLOAD_ROOT;

    @Autowired
    private ContentImageRepository contentImageRepository;

    @Autowired
    private MetadataService metadataService;

    @Override
    public ContentImageEntity createImage(MultipartFile file) throws IOException {
        ContentImageEntity created = null;
        long time = System.currentTimeMillis();

        if (!file.isEmpty()) {
            Files.copy(file.getInputStream(), Paths.get(UPLOAD_ROOT, time + file.getOriginalFilename()));
            created = contentImageRepository.save(new ContentImageEntity(time + file.getOriginalFilename()));
            // https://github.com/drewnoakes/metadata-extractor
            List<String> metadata = metadataService.metadataReader(new File(UPLOAD_ROOT + "\\" + created.getUrl()));
            created.setMetadata(metadata);
        }
        return created;
    }

    @Override
    public ResponseEntity<?> deleteImage(long id, Principal user) {
        return null;
    }
}
