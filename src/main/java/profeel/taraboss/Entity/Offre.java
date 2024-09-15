package profeel.taraboss.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Offre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "duration", nullable = false)
    private Long duration;

    @Column(name = "company", nullable = false)
    private String company;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "keywords", nullable = false,columnDefinition = "TEXT")
    private String keywords;


    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    @Setter(AccessLevel.NONE)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @OneToMany(mappedBy = "offreStage", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Candidature> candidatures ;

    @ManyToOne()
    @JsonBackReference
    private User user;


}
