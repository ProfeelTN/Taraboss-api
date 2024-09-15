package profeel.taraboss.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import profeel.taraboss.DTO.EducationDTO;
import profeel.taraboss.DTO.FileUploadResponse;
import profeel.taraboss.DTO.SkillDTO;
import profeel.taraboss.Entity.Education;
import profeel.taraboss.Entity.Skills;
import profeel.taraboss.Entity.User;
import profeel.taraboss.Entity.UserProfile;
import profeel.taraboss.DTO.UserProfileDTO;
import profeel.taraboss.Repository.UserProfileRepository;
import profeel.taraboss.Repository.UserRepository;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileService fileService;


    public UserProfileDTO createUserProfile(String userProfileDTOstring, MultipartFile resumeFile, MultipartFile imageFile) throws IOException {
        // Deserialize the UserProfileDTO from JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        UserProfileDTO userProfileDTO = objectMapper.readValue(userProfileDTOstring, UserProfileDTO.class);

        // Get the current user from SecurityContext
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //find existing UserProfile bu User id
        UserProfile userProfile = userProfileRepository.findByUserId(user.getId());


        // If no profile exists, create a new one
        if (userProfile == null) {
            userProfile = new UserProfile();
            userProfile.setUser(user);
        }

        if (resumeFile != null && !resumeFile.isEmpty() && imageFile != null && !imageFile.isEmpty()) {
            FileUploadResponse uploadResponse = fileService.uploadFiles(resumeFile,imageFile);
            userProfile.setResumeUrl(uploadResponse.getResumePath());
            userProfile.setCoverPhotoUrl(uploadResponse.getImagePath());
        }

        // Update other UserProfile fields
        userProfile.setDescription(userProfileDTO.getDescription());
        userProfile.setJobTitle(userProfileDTO.getJobTitle());
        userProfile.setPhone(userProfileDTO.getPhone());
        userProfile.setGithubLink(userProfileDTO.getGithub());
        userProfile.setLinkedInLink(userProfileDTO.getLinkedIn());
        userProfile.setLocation(userProfileDTO.getLocation());

        // Save the updated profile to the repository
        userProfile = userProfileRepository.save(userProfile);

        // Convert UserProfile to UserProfileDTO and return
        return convertToDTO(userProfile);
    }

    // Convert UserProfile entity to UserProfileDTO
    private UserProfileDTO convertToDTO(UserProfile userProfile) {
        return UserProfileDTO.builder()
                .id(userProfile.getId())
                .resumeUrl(userProfile.getResumeUrl())
                .coverPhotoUrl(userProfile.getCoverPhotoUrl())
                .jobTitle(userProfile.getJobTitle())
                .description(userProfile.getDescription())
                .phone(userProfile.getPhone())
                .github(userProfile.getGithubLink())
                .linkedIn(userProfile.getLinkedInLink())
                .Location(userProfile.getLocation())
                .email(userProfile.getUser().getEmail())
                .firstName(userProfile.getUser().getFirstName())
                .lastName(userProfile.getUser().getLastName())
                .build();
    }

    // Get the current user's profile
    public UserProfileDTO getCurrentUserProfile() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserProfile userProfile = userProfileRepository.findByUserId(currentUser.getId());
        return convertToDTO(userProfile);
    }

    // Get a user's profile by ID
    public UserProfileDTO getUserProfileById(Long userId) {
        UserProfile userProfile = userProfileRepository.findByUserId(userId);
        return convertToDTO(userProfile);
    }


    public void deleteByUserId(Long userId) {
    }
}
