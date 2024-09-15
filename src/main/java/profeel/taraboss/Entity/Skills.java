package profeel.taraboss.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Skills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int proficiency; // Proficiency as a percentage or a number between 1-10
    @Enumerated(EnumType.STRING)
    private SkillType skillType;

    @ManyToOne
    @JsonBackReference
    private User user;
}
