package profeel.taraboss.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import profeel.taraboss.DTO.EducationDTO;
import profeel.taraboss.DTO.SkillDTO;
import profeel.taraboss.Service.EducationService;
import profeel.taraboss.Service.SkillsService;

import java.util.List;

@RestController
@RequestMapping("/education")
public class EducationController {
    @Autowired
    private EducationService educationService;

        @PostMapping("/add")
        public EducationDTO createEducation(@RequestBody EducationDTO educationDTO) {
            return educationService.createOrUpdateEducation( educationDTO);
        }

    @GetMapping("/me")
    public ResponseEntity<List<EducationDTO>> getCurrentUserProfile() {
        List<EducationDTO> education = educationService.getCurrentUserEducations();
        return ResponseEntity.ok(education);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<EducationDTO>> getEducationByUserId(@PathVariable Long id) {
        List<EducationDTO> skills =educationService.getEducationByUser(id);
        return ResponseEntity.ok(skills);
    }


}
