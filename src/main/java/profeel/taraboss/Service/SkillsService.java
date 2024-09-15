package profeel.taraboss.Service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import profeel.taraboss.DTO.EducationDTO;
import profeel.taraboss.DTO.OffreDTO;
import profeel.taraboss.DTO.SkillDTO;
import profeel.taraboss.DTO.UserProfileDTO;
import profeel.taraboss.Entity.*;
import profeel.taraboss.Repository.SkillsRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillsService implements ISkills{
    @Autowired
    private SkillsRepository skillsRepository;
    @Transactional
    public SkillDTO createOrUpdateSkill(SkillDTO skillDTO) {
        Skills skill;

        // Check if the skill already exists (by ID)
        if (skillDTO.getId() != null) {
            skill = skillsRepository.findById(skillDTO.getId())
                    .orElse(new Skills());
        } else {
            skill = new Skills();
        }

        // Update the fields
        skill.setName(skillDTO.getName());
        skill.setProficiency(skillDTO.getProficiency());
        skill.setSkillType(skillDTO.getSkillType());

        // Associate with the current user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        skill.setUser(user);

        // Save the entity
        Skills savedSkill = skillsRepository.save(skill);

        // Return the updated or created DTO
        return convertToDTO(savedSkill);
    }

    private SkillDTO convertToDTO(Skills skills) {
        SkillDTO skillDTO = new SkillDTO();
        skillDTO.setId(skills.getId());
        skillDTO.setName(skills.getName());
        skillDTO.setProficiency(skills.getProficiency());
        skillDTO.setSkillType(skills.getSkillType());
        return skillDTO ;
    }

    private Skills convertToEntity(SkillDTO skillDTO) {
        Skills skills = new Skills();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        skills.setUser(user);
        skills.setName(skillDTO.getName());
        skills.setProficiency(skillDTO.getProficiency());
        skills.setSkillType(skillDTO.getSkillType());
        return skills;
    }

    public List<SkillDTO> getCurrentUserSkills() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Skills> curentUserskills = skillsRepository.findByUserId(currentUser.getId());
        return curentUserskills.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SkillDTO> getSkillsByUser(Long id) {
        List<Skills> skills = skillsRepository.findByUserId(id);
        return skills.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
