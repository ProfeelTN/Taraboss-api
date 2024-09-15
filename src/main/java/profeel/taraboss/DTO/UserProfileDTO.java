package profeel.taraboss.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import profeel.taraboss.Entity.Education;
import profeel.taraboss.Entity.Skills;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfileDTO {
    private Long id;
    private String resumeUrl;
    private String coverPhotoUrl;
    private String jobTitle;
    private String description;
    private String phone;
    private String github;
    private String linkedIn;
    private String Location;
    private String firstName;
    private String lastName;
    private String email;
}



