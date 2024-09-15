package profeel.taraboss.Controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import profeel.taraboss.DTO.FileUploadResponse;
import profeel.taraboss.DTO.UserProfileDTO;
import profeel.taraboss.Entity.UserProfile;
import profeel.taraboss.Service.FileService;
import profeel.taraboss.Entity.User;

@RestController
@Slf4j
@RequestMapping("/test")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadFiles(@RequestPart("userProfileDTO") UserProfileDTO userProfileDTO,
                                                          @RequestParam("imageFile") MultipartFile imageFile,
                                                          @RequestParam("resumeFile") MultipartFile resumeFile) throws FileUploadException {

        return new ResponseEntity<>(fileService.uploadFiles(imageFile, resumeFile), HttpStatus.OK);
    }

}
