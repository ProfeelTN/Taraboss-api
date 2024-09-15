package profeel.taraboss.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import profeel.taraboss.Config.JwtService;
import profeel.taraboss.DTO.ForgetPassword;
import profeel.taraboss.DTO.Mail;
import profeel.taraboss.DTO.UserDTO;
import profeel.taraboss.DTO.resetPasswordRequest;
import profeel.taraboss.Entity.Role;
import profeel.taraboss.Entity.Token;
import profeel.taraboss.Entity.User;
import profeel.taraboss.Repository.*;
import software.amazon.awssdk.regions.servicemetadata.ApiEcrPublicServiceMetadata;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private SkillsRepository skillsRepository;
    @Autowired
    private EducationRepository educationRepository;
    @Autowired
    private ExperienceRepository experienceRepository;
    @Autowired
    private CandidatureRepository candidatureRepository;
    @Autowired
    private TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserDetailsService userDetailsService;

    private final String clientUrl = "http://localhost:4200/resetPassword";

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .filter(user -> !user.getRole().equals(Role.ADMIN))  // Exclude ADMIN role
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getRole(),
                        user.getCreatedAt()  // Add the createdAt field mapping
                ))
                .collect(Collectors.toList());
    }


    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    @Transactional
    public void deleteById(Long userId) {
        // Delete associated foreign keys
        userProfileRepository.deleteByUserId(userId);
        skillsRepository.deleteByUserId(userId);
        educationRepository.deleteByUserId(userId);
        experienceRepository.deleteByUserId(userId);
        candidatureRepository.deleteOffreCandidature(userId);

        // Finally, delete the user
        userRepository.deleteById(userId);
    }


    @Override
    public Optional<User> getCurrentUser(String token) {
        String username = jwtService.extractUsername(token);
        return userRepository.findByEmail(username);
    }

    @Override
    public void resetPassword(resetPasswordRequest request) {
        // Retrieve user by email
        User admin = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found"));

        // Check if the current password matches
        if (!passwordEncoder.matches(request.getCurrentPassword(), admin.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        // Check if the new password matches the confirmed password
        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw new IllegalArgumentException("New password and confirm password do not match");
        }

        // Update user's password
        admin.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // Save the updated user
        userRepository.save(admin);
    }

    @Override
    public void forgetPassword(ForgetPassword forgetPassword) throws jakarta.mail.MessagingException {
        Optional<User> optionalUser = userRepository.findByEmail(forgetPassword.getEmail());
        if (optionalUser.isPresent()) {
            User user =optionalUser.get();
            // Generate a new random password

            final UserDetails userDetails = userDetailsService.loadUserByUsername(forgetPassword.getEmail());
            String token = jwtService.generateToken(new HashMap<>(),userDetails);

            // Create a new Token object and set the generated token
            Token userToken = user.getToken();
            if (userToken == null) {
                userToken = new Token();
                user.setToken(userToken);
            }
            userToken.setToken(token);

            // Save the Token object first
            tokenRepository.save(userToken);

            String newPassword = generateRandomPassword();

            // Encrypt the new password
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(newPassword);

            // Update the user's password
            user.setPassword(encodedPassword);

            userRepository.save(user);

            final String subject = "Reset Password";
            String body = "<div><h3>Hello " + user.getFirstName() +" "+user.getLastName()+ " </h3>" +
                    "<br>" +
                    "<h4>A password reset request has been requested for your user account  </h4>" +
                    "<h4>This your new password : "+ newPassword + "</h4>" +
                    "<br>" +  "<h4>If you need help, please contact the website administrator.</h4>" +
                    "<br>" +
                    "<h4>Admin</h4></div>";
            Mail mail = new Mail(forgetPassword.getEmail(), subject, body);
            emailService.sendMail(mail);
        } else {
            // Log the error
            System.out.println("User not found for email: " + forgetPassword.getEmail());
            throw new NotFoundException("User not found");
        }
    }

    public Long getCurrentUserId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return  user.getId();
    }

    public void accepteCandidature(){

    }


        public static class UnauthorizedUserException extends RuntimeException {
        public UnauthorizedUserException(String message) {
            super(message);
        }
    }

    private boolean userAlreadyExist(String email){
        Optional<User> Admin = userRepository.findByEmail(email);
        return Admin.isPresent();
    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 10;
    private static final Random RANDOM = new SecureRandom();
    private String generateRandomPassword() {
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return password.toString();
    }

}
