package profeel.taraboss.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationDTO {
    private Long id;
    private String title;
    private String institute;
    private String year;
}
