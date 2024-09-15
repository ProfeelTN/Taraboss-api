package profeel.taraboss.Service;

import jakarta.mail.MessagingException;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import profeel.taraboss.DTO.CandidatureDTO;
import profeel.taraboss.DTO.Mail;
import profeel.taraboss.Entity.Candidature;
import profeel.taraboss.Entity.Offre;
import profeel.taraboss.Entity.User;
import profeel.taraboss.Repository.CandidatureRepository;
import profeel.taraboss.Repository.OffreRepository;
import profeel.taraboss.Repository.UserRepository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CandidatureService implements ICandidature {
    @Autowired
    private CandidatureRepository candidatureRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OffreRepository offreStageRepository;

    @Autowired
    private EmailService emailService;

    public CandidatureDTO createCandidature(CandidatureDTO candidatureDTO) {

        Offre offreStage = offreStageRepository.findById(candidatureDTO.getOffreId()).orElseThrow(() -> new NotFoundException("OffreStage not found"));
        User user = getCurrentUser();
        Candidature candidature = new Candidature();
        candidature.setUser(user);
        candidature.setOffreStage(offreStage);
        candidature.setStatut("En Attente");

        Candidature savedCandidature = candidatureRepository.save(candidature);

        return mapToDTO(savedCandidature);
    }

    @Override
    public List<CandidatureDTO> getCandidatureById(Long id) {
        List<Candidature> candidatures = candidatureRepository.findByUserId(id);
        return candidatures.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<CandidatureDTO> getAllCandidatures() {
        List<Candidature> candidatures = candidatureRepository.findAll();
        return candidatures.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteCandidature(Long id) {
        Candidature candidature = candidatureRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidature not found"));
        candidatureRepository.delete(candidature);
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(username).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public void acceptCandidature(Long candidatureId) throws MessagingException {
        Candidature candidature = candidatureRepository.findById(candidatureId)
                .orElseThrow(() -> new NotFoundException("Candidature not found"));

        // Set status to accepted
        candidature.setStatut("Accepted");
        candidatureRepository.save(candidature);

        // Send acceptance email
        String subject = "Your Candidature is Accepted!";
        String message = "Dear " + candidature.getUser().getFirstName() + ",\n\n" +
                "We are pleased to inform you that your candidature for the job '" +
                candidature.getOffreStage().getTitle() + "' has been accepted.\n\n" +
                "Best regards,\nCompany Team";
        Mail mail=new Mail(candidature.getUser().getEmail(),subject,message);
        emailService.sendMail(mail);
    }

    public void refuseCandidature(Long candidatureId) throws MessagingException {
        Candidature candidature = candidatureRepository.findById(candidatureId)
                .orElseThrow(() -> new NotFoundException("Candidature not found"));

        // Set status to refused
        candidature.setStatut("Refused");
        candidatureRepository.save(candidature);

        // Send refusal email
        String subject = "Your Candidature is Refused";
        String message = "Dear " + candidature.getUser().getFirstName() + ",\n\n" +
                "We regret to inform you that your candidature for the job '" +
                candidature.getOffreStage().getTitle() + "' has been refused.\n\n" +
                "Best regards,\nCompany Team";
        Mail mail=new Mail(candidature.getUser().getEmail(),subject,message);
        emailService.sendMail(mail);
    }

    private CandidatureDTO mapToDTO(Candidature candidature) {
        CandidatureDTO dto = new CandidatureDTO();
        dto.setId(candidature.getId());
        dto.setUserId(candidature.getUser().getId());
        dto.setFirstName(candidature.getUser().getFirstName());
        dto.setLastName(candidature.getUser().getLastName());
        dto.setEmail(candidature.getUser().getEmail());

        System.out.println("FirstName: " + candidature.getUser().getFirstName());
        System.out.println("LastName: " + candidature.getUser().getLastName());
        System.out.println("Email: " + candidature.getUser().getEmail());

        if (candidature.getUser().getUserProfile() != null) {
            dto.setJobTitle(candidature.getUser().getUserProfile().getJobTitle());
            dto.setCoverPhotoUrl(candidature.getUser().getUserProfile().getCoverPhotoUrl());
            dto.setGithub(candidature.getUser().getUserProfile().getGithubLink());
            dto.setLinkedin(candidature.getUser().getUserProfile().getLinkedInLink());
            dto.setResume(candidature.getUser().getUserProfile().getResumeUrl());
            dto.setDescription(candidature.getUser().getUserProfile().getDescription());
            dto.setLocation(candidature.getUser().getUserProfile().getLocation());
            dto.setPhone(candidature.getUser().getUserProfile().getPhone());
            dto.setStatut(candidature.getStatut());
            dto.setCreatedAt(candidature.getCreatedAt());
        }

        dto.setOffreId(candidature.getOffreStage().getId());
        dto.setOffreTitle(candidature.getOffreStage().getTitle());

        return dto;
    }

}

