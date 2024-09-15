package profeel.taraboss.Service;

import org.springframework.transaction.annotation.Transactional;
import profeel.taraboss.DTO.CandidatureDTO;

import java.util.List;

public interface ICandidature {
    @Transactional
    CandidatureDTO createCandidature(CandidatureDTO candidatureDTO);

    List<CandidatureDTO> getAllCandidatures();

    void deleteCandidature(Long id);

    List<CandidatureDTO> getCandidatureById(Long id);
}
