package profeel.taraboss.DTO;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import profeel.taraboss.Entity.SkillType;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillDTO {
    private Long id;
    private String name;
    private int proficiency;
    @Enumerated(EnumType.STRING)
    private SkillType skillType;
}
