package profeel.taraboss.Controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import profeel.taraboss.DTO.OffreDTO;
import profeel.taraboss.Entity.Type;
import profeel.taraboss.Service.OffreService;

import java.util.List;

@RestController
@RequestMapping("/offre")
public class OffreController {

    @Autowired
    private OffreService offreStageService;

    @GetMapping("/type")
    public Type[] getAllTypes() {
        return Type.values();
    }

    @PostMapping
    public OffreDTO createOffreStage(@RequestBody OffreDTO offreStageDTO) {
        return offreStageService.createOffreStage(offreStageDTO);
    }

    @PutMapping("/{id}")
    public OffreDTO updateOffreStage(@PathVariable Long id, @RequestBody OffreDTO offreStageDTO) {
        return offreStageService.updateOffreStage(id, offreStageDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOffreStage(@PathVariable Long id) {
        try {
            offreStageService.deleteOffreStage(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Handle exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public OffreDTO getOffreStage(@PathVariable Long id) {
        return offreStageService.getOffreStage(id);
    }

    @GetMapping
    public List<OffreDTO> getAllOffreStages() {
        return offreStageService.getAllOffreStages();
    }

    @GetMapping("/search")
    public List<OffreDTO> searchOffres(@RequestParam(required = false) String poste,
                                    @RequestParam(required = false) String location) {
        return offreStageService.searchOffres(poste, location);
    }
    @GetMapping("/{offreId}/keywords")
    public ResponseEntity<List<String>> getKeywordsByOffreId(@PathVariable Long offreId) {
        List<String> keywords = offreStageService.getKeywordsByOffreId(offreId);
        if (keywords != null) {
            return ResponseEntity.ok(keywords);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
