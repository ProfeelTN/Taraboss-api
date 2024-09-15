package profeel.taraboss.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import profeel.taraboss.DTO.OffreDTO;
import profeel.taraboss.Entity.Offre;

import java.util.List;

public interface OffreRepository extends JpaRepository<Offre,Long> {
    List<OffreDTO> findByTitleAndLocation(String poste, String location);
    @Query("SELECT o.keywords FROM Offre o WHERE o.id = :offreId")
    List<String> findKeywordsByOffreId(Long offreId);

}
