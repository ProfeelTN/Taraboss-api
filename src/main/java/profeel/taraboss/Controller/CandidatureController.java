package profeel.taraboss.Controller;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.webjars.NotFoundException;
import profeel.taraboss.DTO.CandidatureDTO;
import profeel.taraboss.Entity.Candidature;
import profeel.taraboss.Entity.Offre;
import profeel.taraboss.Entity.User;
import profeel.taraboss.Repository.CandidatureRepository;
import profeel.taraboss.Repository.OffreRepository;
import profeel.taraboss.Service.CandidatureService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/candidature")
public class CandidatureController {

    @Autowired
    private CandidatureService candidatureService;
    @Autowired
    CandidatureRepository candidatureRepository;
    @Autowired
    OffreRepository offreStageRepository;

    @PostMapping("/add")
    public ResponseEntity<CandidatureDTO> createCandidature(@RequestBody CandidatureDTO candidatureDTO) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Offre offreStage = offreStageRepository.findById(candidatureDTO.getOffreId())
                    .orElseThrow(() -> new NotFoundException("OffreStage not found"));

            Optional<Candidature> existingCandidature = candidatureRepository.findByUserAndOffreStage(user, offreStage);
            if (existingCandidature.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // Return 409 Conflict
            }
            if (user.getUserProfile() == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null); // Return 403 Forbidden
            }
            if (candidatureDTO.getOffreId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(null); // Return 400 Bad Request for null ID
            }

            CandidatureDTO createdCandidature = candidatureService.createCandidature(candidatureDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCandidature);

        } catch (NotFoundException ex) {
            // Handle NotFoundException
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<List<CandidatureDTO>> getCandidatureById(@PathVariable Long id) {
        List<CandidatureDTO> candidatureDTO = candidatureService.getCandidatureById(id);
        return ResponseEntity.ok(candidatureDTO);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CandidatureDTO>> getAllCandidatures() {
        List<CandidatureDTO> candidatures = candidatureService.getAllCandidatures();
        return ResponseEntity.ok(candidatures);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCandidature(@PathVariable Long id) {
        candidatureService.deleteCandidature(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/accept")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> acceptCandidature(@PathVariable Long id) {
        try {
            candidatureService.acceptCandidature(id);
            return ResponseEntity.ok("Candidature accepted successfully"); // HTTP 200
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while accepting candidature");
        }
    }

    @PostMapping("/{id}/refuse")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> refuseCandidature(@PathVariable Long id) {
        try {
            candidatureService.refuseCandidature(id);
            return ResponseEntity.ok("Candidature refused successfully"); // HTTP 200
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while refusing candidature");
        }
    }


}
