package profeel.taraboss.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import profeel.taraboss.DTO.EducationDTO;
import profeel.taraboss.DTO.SkillDTO;
import profeel.taraboss.Entity.Education;
import profeel.taraboss.Entity.Skills;
import profeel.taraboss.Entity.User;
import profeel.taraboss.Repository.EducationRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EducationService implements IEducation{
    @Autowired
    private EducationRepository educationRepository;

    // Create or Update Education
    public EducationDTO createOrUpdateEducation(EducationDTO educationDTO) {
        Education education = educationDTO.getId() != null
                ? educationRepository.findById(educationDTO.getId()).orElse(new Education())
                : new Education();

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        education.setUser(currentUser);
        education.setTitle(educationDTO.getTitle());
        education.setInstitute(educationDTO.getInstitute());
        education.setYear(educationDTO.getYear());

        Education savedEducation = educationRepository.save(education);
        return convertToDTO(savedEducation);
    }

    // Retrieve All Educations of Current User
    public List<EducationDTO> getCurrentUserEducations() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return educationRepository.findByUserId(currentUser.getId())
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Retrieve Education by ID
    public List<EducationDTO> getEducationByUser(Long id) {
        List<Education> educations = educationRepository.findByUserId(id);
        return educations.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Delete Education
    public void deleteEducation(Long id) {
        educationRepository.deleteById(id);
    }

    private EducationDTO convertToDTO(Education education) {
        EducationDTO dto = new EducationDTO();
        dto.setId(education.getId());
        dto.setTitle(education.getTitle());
        dto.setInstitute(education.getInstitute());
        dto.setYear(education.getYear());
        return dto;
    }

}
