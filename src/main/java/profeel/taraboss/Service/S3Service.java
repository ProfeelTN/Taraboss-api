//package profeel.taraboss.Service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.PutObjectRequest;
//
//import java.io.IOException;
//import java.nio.file.Paths;
//
//@Service
//public class S3Service {
//
//    @Autowired
//    private S3Client s3Client;
//
//    @Value("${aws.s3.bucket.name}")
//    private String bucketName;
//
//    public String uploadFile(MultipartFile file, String keyPrefix) throws IOException {
//        String key = keyPrefix + "/" + file.getOriginalFilename();
//        s3Client.putObject(PutObjectRequest.builder()
//                        .bucket(bucketName)
//                        .key(key)
//                        .build(),
//                Paths.get(file.getOriginalFilename()));
//
//        return key;
//    }
//}
//
