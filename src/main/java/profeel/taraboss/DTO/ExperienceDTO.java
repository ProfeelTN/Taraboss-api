package profeel.taraboss.DTO;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExperienceDTO {

        private Long id;
        private String poste;
        private String description;
        private String entreprise;
        private String year;

}
