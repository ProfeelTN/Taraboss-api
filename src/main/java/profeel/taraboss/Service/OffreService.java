package profeel.taraboss.Service;

import jakarta.transaction.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import profeel.taraboss.DTO.CandidatureDTO;
import profeel.taraboss.DTO.OffreDTO;
import profeel.taraboss.Entity.Candidature;
import profeel.taraboss.Entity.Offre;
import profeel.taraboss.Repository.CandidatureRepository;
import profeel.taraboss.Repository.OffreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OffreService implements IOffre {
    @Autowired
    private OffreRepository offreRepository;
    @Autowired
    private CandidatureRepository candidatureRepository;


    @Transactional
    @Override
    public OffreDTO createOffreStage(OffreDTO offreDTO) {
        Offre offreStage = convertToEntity(offreDTO);
        Offre savedOffreStage = offreRepository.save(offreStage);
        return convertToDTO(savedOffreStage);
    }

    @Transactional
    @Override
    public OffreDTO updateOffreStage(Long id, OffreDTO offreStageDTO) {
        Offre offreStage = offreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("OffreStage not found"));
        offreStage.setTitle(offreStageDTO.getTitle());
        offreStage.setDescription(offreStageDTO.getDescription());
        offreStage.setDuration(offreStageDTO.getDuration());
        offreStage.setCompany(offreStageDTO.getCompany());
        offreStage.setLocation(offreStage.getLocation());
        offreStage.setType(offreStage.getType());
        Offre updatedOffreStage = offreRepository.save(offreStage);
        return convertToDTO(updatedOffreStage);
    }

    @Transactional
    @Override
    public void deleteOffreStage(Long id) {
        List<Candidature>candidatures =candidatureRepository.findByOffreStageId(id);
        if (!candidatures.isEmpty()) {
            candidatureRepository.deleteOffreCandidature(id);
        }

        // Step 3: Delete the offre
        offreRepository.deleteById(id);
    }
    @Override
    public OffreDTO getOffreStage(Long id) {
        Offre offreStage = offreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("OffreStage not found"));
        return convertToDTO(offreStage);
    }

    public List<OffreDTO> searchOffres(String poste, String location) {
        return offreRepository.findByTitleAndLocation(poste, location);
    }

    public List<String> getKeywordsByOffreId(Long offreId) {
        return offreRepository.findKeywordsByOffreId(offreId);
    }

    @Override
    public List<OffreDTO> getAllOffreStages() {
        List<Offre> offreStages = offreRepository.findAll();
        return offreStages.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private OffreDTO convertToDTO(Offre offreStage) {
        OffreDTO offreStageDTO = new OffreDTO();
        offreStageDTO.setId(offreStage.getId());
        offreStageDTO.setTitle(offreStage.getTitle());
        offreStageDTO.setDescription(offreStage.getDescription());
        offreStageDTO.setDuration(offreStage.getDuration());
        offreStageDTO.setCompany(offreStage.getCompany());
        offreStageDTO.setLocation(offreStage.getLocation());
        offreStageDTO.setType(offreStage.getType());
        offreStageDTO.setKeywords(offreStage.getKeywords());
        offreStageDTO.setCreatedAt(offreStage.getCreatedAt());

        int candiaturesCount= (offreStage.getCandidatures() !=null)? offreStage.getCandidatures().size():0;
        offreStageDTO.setCandidatureCount(candiaturesCount);

        return offreStageDTO;
    }

    private Offre convertToEntity(OffreDTO offreStageDTO) {
        Offre offreStage = new Offre();
        offreStage.setTitle(offreStageDTO.getTitle());
        offreStage.setDescription(offreStageDTO.getDescription());
        offreStage.setDuration(offreStageDTO.getDuration());
        offreStage.setCompany(offreStageDTO.getCompany());
        offreStage.setLocation(offreStageDTO.getLocation());
        offreStage.setType(offreStageDTO.getType());
        offreStage.setKeywords(offreStageDTO.getKeywords());
        return offreStage;
    }
}
