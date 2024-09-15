package profeel.taraboss.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import profeel.taraboss.DTO.ExperienceDTO;
import profeel.taraboss.DTO.OffreDTO;
import profeel.taraboss.DTO.SkillDTO;
import profeel.taraboss.DTO.UserProfileDTO;
import profeel.taraboss.Service.SkillsService;

import java.util.List;
@RestController
@RequestMapping("/skills")
public class SkillsController {

    @Autowired
    private SkillsService skillsService;

    @PostMapping("/add")
    public SkillDTO createSkills(@RequestBody SkillDTO skillDTO) {
        return skillsService.createOrUpdateSkill(skillDTO);
    }

    @GetMapping("/me")
    public ResponseEntity<List<SkillDTO>> getCurrentUserProfile() {
    List<SkillDTO> skills = skillsService.getCurrentUserSkills();
    return ResponseEntity.ok(skills);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<SkillDTO>> getEducationByUserId(@PathVariable Long id) {
        List<SkillDTO> skills =skillsService.getSkillsByUser(id);
        return ResponseEntity.ok(skills);
    }

}
