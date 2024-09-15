package profeel.taraboss.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import profeel.taraboss.Entity.Candidature;
import profeel.taraboss.Entity.Offre;
import profeel.taraboss.Entity.User;

import java.util.List;
import java.util.Optional;

public interface CandidatureRepository extends JpaRepository<Candidature,Long> {
    Optional<Candidature> findByUserAndOffreStage(User user, Offre offreStage);

    List<Candidature> findByUserId(Long id);


    List<Candidature> findByOffreStageId(long id);

    @Modifying
    @Query("DELETE FROM Candidature c WHERE c.offreStage.id  = :id")
    void deleteOffreCandidature(@Param("id") Long offreId);


}
