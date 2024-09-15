package profeel.taraboss.Controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import profeel.taraboss.Auth.AuthenticationService;
import profeel.taraboss.DTO.AuthResponse;
import profeel.taraboss.DTO.AuthenticateRequest;
import profeel.taraboss.DTO.AuthenticationResponse;
import profeel.taraboss.DTO.RegisterRequest;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;


    @PostMapping("/registerStagaire")
    public ResponseEntity<AuthenticationResponse> registerStagaire(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.registerStagaire(request));
    }

    @PostMapping("/registerEncadrant")
    public ResponseEntity<AuthenticationResponse> registerEncadrant(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.registerEncadrant(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthenticateRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));

    }
}
