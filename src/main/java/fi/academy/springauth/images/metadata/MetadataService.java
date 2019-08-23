package fi.academy.springauth.images.metadata;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifReader;
import fi.academy.springauth.images.ImageEntity;
import fi.academy.springauth.images.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MetadataService {

    @Autowired
    private MetadataRepository metadataRepository;
    @Autowired
    private ImageRepository imageRepository;

    public void metadataReader(File image, ImageEntity file) {
        try {
            ExifReader ex = new ExifReader();
            Metadata metadata = ImageMetadataReader.readMetadata(image);

            for (Directory directory : metadata.getDirectories()) {

                if (directory.getName().contains("Exif")) {
                    for (Tag tag : directory.getTags()) {
                        System.out.println(tag);
                        MetadataEntity me = new MetadataEntity();
                        me.setMetadata(tag.getTagName() + " : " + tag.getDescription());
                        me.setImage(file);

                        metadataRepository.save(me);

                    }
                }

                for (String error : directory.getErrors()) {
                    System.err.println("ERROR: " + error);
                }

            }

        } catch (ImageProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
