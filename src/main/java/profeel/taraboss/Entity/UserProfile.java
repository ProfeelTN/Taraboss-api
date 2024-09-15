package profeel.taraboss.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import profeel.taraboss.DTO.EducationDTO;
import profeel.taraboss.DTO.SkillDTO;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserProfile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jobTitle;
    private String resumeUrl;
    private String coverPhotoUrl;
    private String description;
    private String phone;
    private String GithubLink;
    private String LinkedInLink;
    private String Location;



    @OneToOne()
    @JsonBackReference

    private User user;

}

