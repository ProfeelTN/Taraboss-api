package profeel.taraboss.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import profeel.taraboss.DTO.UserProfileDTO;
import profeel.taraboss.Entity.User;
import profeel.taraboss.Entity.UserProfile;
import profeel.taraboss.Service.UserProfileService;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.io.IOException;
@RestController
@RequestMapping("/profile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @PostMapping(path = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<UserProfileDTO> uploadFilesAndCreateUserProfile(
            @RequestPart("userProfileDTO") String userProfileDTOstring,

            @RequestParam("imageFile") MultipartFile imageFile,

            @RequestParam("resumeFile") MultipartFile resumeFile) throws IOException {


       System.out.println("Received productDTO: " + userProfileDTOstring);

        UserProfileDTO createdProfile = userProfileService.createUserProfile(userProfileDTOstring, imageFile,resumeFile);
        return new ResponseEntity<>(createdProfile, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDTO> getCurrentUserProfile() {
        UserProfileDTO userProfile = userProfileService.getCurrentUserProfile();
        return ResponseEntity.ok(userProfile);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileDTO> getUserProfileById(@PathVariable Long userId) {
        UserProfileDTO userProfile = userProfileService.getUserProfileById(userId);
        return ResponseEntity.ok(userProfile);
    }
}