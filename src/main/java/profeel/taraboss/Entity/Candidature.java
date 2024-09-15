package profeel.taraboss.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Candidature implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String statut;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    @Setter(AccessLevel.NONE)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offre_stage_id")
    @JsonBackReference
    private Offre offreStage;

}
