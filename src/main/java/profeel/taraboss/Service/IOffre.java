package profeel.taraboss.Service;

import jakarta.transaction.Transactional;
import profeel.taraboss.DTO.OffreDTO;

import java.util.List;

public interface IOffre {
    @Transactional
    OffreDTO createOffreStage(OffreDTO offreDTO);

    @Transactional
    OffreDTO updateOffreStage(Long id, OffreDTO offreStageDTO);

    @Transactional
    void deleteOffreStage(Long id);

    OffreDTO getOffreStage(Long id);

    List<OffreDTO> getAllOffreStages();
}
