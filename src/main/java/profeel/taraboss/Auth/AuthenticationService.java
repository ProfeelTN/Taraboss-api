package profeel.taraboss.Auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import profeel.taraboss.Config.JwtService;
import profeel.taraboss.DTO.AuthResponse;
import profeel.taraboss.DTO.AuthenticateRequest;
import profeel.taraboss.DTO.AuthenticationResponse;
import profeel.taraboss.DTO.RegisterRequest;
import profeel.taraboss.Entity.Role;
import profeel.taraboss.Entity.User;
import profeel.taraboss.Repository.UserRepository;

import java.util.Date;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImp userDetailsService;

    public AuthenticationResponse registerStagaire(RegisterRequest request) {
        try {
            var user = User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.STAGAIRE)
                    .enabled(Boolean.TRUE)
                    .createdAt(new Date())
                    .build();

            userRepository.save(user);

            var jwtToken = jwtService.generateToken(new HashMap<>(), user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();

        } catch (DataIntegrityViolationException e) {
            return AuthenticationResponse.builder()
                    .message("Email already exists")
                    .build();
        }
    }

    public AuthenticationResponse registerEncadrant(RegisterRequest request) {
        try {
            var user = User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.ENCADRANT)
                    .enabled(Boolean.TRUE)
                    .build();

            userRepository.save(user);

            var jwtToken = jwtService.generateToken(new HashMap<>(), user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();

        } catch (DataIntegrityViolationException e) {
            return AuthenticationResponse.builder()
                    .message("Email already exists")
                    .build();
        }
    }

    public AuthResponse authenticate(AuthenticateRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(request.getEmail());


            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow();
            var jwtToken = jwtService.generateToken(new HashMap<>(),user);
            return new AuthResponse(200,AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build(), "login successfully");
        }catch (BadCredentialsException e) {
            return new AuthResponse(404, "Invalid email or password");
        }
        catch (DisabledException d) {
            return new AuthResponse(404, "Account disabled ! check you email to enabled it.");

        }


    }


}
