package profeel.taraboss.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import profeel.taraboss.DTO.EducationDTO;
import profeel.taraboss.DTO.ExperienceDTO;
import profeel.taraboss.Entity.Education;
import profeel.taraboss.Entity.Experience;
import profeel.taraboss.Entity.User;
import profeel.taraboss.Repository.ExperienceRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExperienceService implements IExperience {
    @Autowired
    private ExperienceRepository experienceRepository;

    public ExperienceDTO createOrUpdateExperience(ExperienceDTO experienceDTO) {
        Experience experience;

        // Check if the experience already exists (by ID)
        if (experienceDTO.getId() != null) {
            experience = experienceRepository.findById(experienceDTO.getId())
                    .orElse(new Experience());
        } else {
            experience = new Experience();
        }

        // Update the fields
        experience.setId(experienceDTO.getId());
        experience.setEntreprise(experienceDTO.getEntreprise());
        experience.setPoste(experienceDTO.getPoste());
        experience.setDescription(experienceDTO.getDescription());
        experience.setYear(experienceDTO.getYear());

        // Associate with the current user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        experience.setUser(user);

        // Save the entity
        Experience savedExperience = experienceRepository.save(experience);

        // Return the updated or created DTO
        return convertToDTO(savedExperience);
    }
    private ExperienceDTO convertToDTO(Experience experience) {
        ExperienceDTO experienceDTO = new ExperienceDTO();
        experienceDTO.setId(experience.getId());
        experienceDTO.setEntreprise(experience.getEntreprise());
        experienceDTO.setDescription(experience.getDescription());
        experienceDTO.setYear(experience.getYear());
        experienceDTO.setPoste(experience.getPoste());
        return experienceDTO ;
    }


    public List<ExperienceDTO> getCurrentUserExperience() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Experience> experiences = experienceRepository.findByUserId(currentUser.getId());
        return experiences.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ExperienceDTO> getExperienceByUser(Long id) {
        List<Experience> experiences = experienceRepository.findByUserId(id);
        return experiences.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
