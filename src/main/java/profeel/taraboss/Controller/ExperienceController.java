package profeel.taraboss.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import profeel.taraboss.DTO.EducationDTO;
import profeel.taraboss.DTO.ExperienceDTO;
import profeel.taraboss.Service.ExperienceService;

import java.util.List;
@RestController
@RequestMapping("/experience")
public class ExperienceController {

    @Autowired
    private ExperienceService experienceService;

    @PostMapping("/add")
    public ExperienceDTO createExperience(@RequestBody ExperienceDTO experienceDTO) {
        return experienceService.createOrUpdateExperience( experienceDTO);
    }

    @GetMapping("/me")
    public ResponseEntity<List<ExperienceDTO>> getCurrentUserExperience() {
        List<ExperienceDTO> education = experienceService.getCurrentUserExperience();
        return ResponseEntity.ok(education);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ExperienceDTO>> getEducationByUserId(@PathVariable Long id) {
        List<ExperienceDTO> educations =experienceService.getExperienceByUser(id);
        return ResponseEntity.ok(educations);
    }

}
