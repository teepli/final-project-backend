package fi.academy.springauth.content;

import fi.academy.springauth.images.ImageEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Service
@Profile("prod")
public class AwsImageService implements fi.academy.springauth.utils.ImageService {

    @Override
    public ImageEntity createImage(MultipartFile file) throws IOException {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteImage(long id, Principal user) {
        return null;
    }
}