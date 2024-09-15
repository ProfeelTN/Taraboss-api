package profeel.taraboss.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CandidatureDTO {
    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String jobTitle; // From UserProfile
    private String coverPhotoUrl; // From UserProfile
    private Long offreId;
    private String offreTitle;
    private String email;
    private String github;
    private String linkedin;
    private String phone;
    private String resume;
    private String description;
    private String location;
    private String statut;

    private String message;
    private Date createdAt;


}
