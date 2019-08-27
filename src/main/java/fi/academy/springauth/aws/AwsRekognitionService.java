package fi.academy.springauth.aws;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

@Service
public class AwsRekognitionService {

    @Value("${aws.s3.audio.bucket}")
    private String bucket;

    @Autowired
    private AmazonRekognition client;

    public AwsRekognitionService(AmazonRekognition client) {
        this.client = client;
    }

    public List<Label> detectUploadedLabelsResult(MultipartFile imageToCheck) throws IOException {
        DetectLabelsRequest request = new DetectLabelsRequest()
                .withImage(new Image().withBytes(ByteBuffer.wrap(imageToCheck.getBytes())))
                .withMaxLabels(10)
                .withMinConfidence(80F);

        DetectLabelsResult dl = client.detectLabels(request);
        return dl.getLabels();
    }

    public List<Label> detectS3ImageLabelsResults(String photo) {
        DetectLabelsRequest request = new DetectLabelsRequest()
                .withImage(new Image()
                        .withS3Object(new S3Object()
                                .withName(photo).withBucket(bucket)))
                .withMaxLabels(10)
                .withMinConfidence(80F);

        DetectLabelsResult dl = client.detectLabels(request);
        return dl.getLabels();
    }

}