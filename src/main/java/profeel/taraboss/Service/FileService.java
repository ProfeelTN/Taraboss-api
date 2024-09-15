package profeel.taraboss.Service;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import profeel.taraboss.DTO.FileUploadResponse;
import profeel.taraboss.Entity.User;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Value("${aws.accessKeyId}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    private AmazonS3 s3Client;

    @PostConstruct
    private void initialize() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(region)
                .build();
    }


    public FileUploadResponse uploadFiles(MultipartFile imageFile, MultipartFile resumeFile) throws FileUploadException {
        FileUploadResponse fileUploadResponse = new FileUploadResponse();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = user.getId();

        try {
            // Upload the image file
            if (imageFile != null && !imageFile.isEmpty()) {
                String imagePath = "images/" + userId + "/coverPhoto.jpg";
                ObjectMetadata imageMetadata = new ObjectMetadata();
                imageMetadata.setContentType(imageFile.getContentType());
                imageMetadata.setContentLength(imageFile.getSize());
                s3Client.putObject(bucketName, imagePath, imageFile.getInputStream(), imageMetadata);

                // Generate the image file URL
                URL imageUrl = s3Client.getUrl(bucketName, imagePath);
                fileUploadResponse.setImagePath(imageUrl.toString());
            }

            // Upload the resume file
            if (resumeFile != null && !resumeFile.isEmpty()) {
                String resumePath = "resumes/" + userId + "/resume.pdf";
                ObjectMetadata resumeMetadata = new ObjectMetadata();
                resumeMetadata.setContentType(resumeFile.getContentType());
                resumeMetadata.setContentLength(resumeFile.getSize());
                s3Client.putObject(bucketName, resumePath, resumeFile.getInputStream(), resumeMetadata);

                // Generate the resume file URL
                URL resumeUrl = s3Client.getUrl(bucketName, resumePath);
                fileUploadResponse.setResumePath(resumeUrl.toString());
            }

            fileUploadResponse.setDateTime(LocalDateTime.now());

        } catch (IOException e) {
            log.error("Error occurred ==> {}", e.getMessage());
            throw new FileUploadException("Error occurred in file upload ==> " + e.getMessage());
        }

        return fileUploadResponse;
    }
}
