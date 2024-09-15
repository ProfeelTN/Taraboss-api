package profeel.taraboss.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import profeel.taraboss.DTO.ForgetPassword;
import profeel.taraboss.DTO.UserDTO;
import profeel.taraboss.DTO.resetPasswordRequest;
import profeel.taraboss.Entity.User;
import profeel.taraboss.Service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/currentUser")
    public ResponseEntity<User> getCurrentUser(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7); // Remove "Bearer " prefix
        Optional<User> currentUser = userService.getCurrentUser(jwtToken);
        return currentUser.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/Id")
    public Long getCurrentUserId() {
        return userService.getCurrentUserId();
    }

    @PostMapping("/forgetPassword")
    public void forgetPassword(@RequestBody ForgetPassword email) throws Exception  {
        userService.forgetPassword(email);
    }
    @PostMapping("/resetPassword")
    public void resetPassword(@RequestBody resetPasswordRequest request)  {
        userService.resetPassword(request);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/id")
    public ResponseEntity<Optional<User>> findUserById(Long id) {
        Optional<User> user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable  Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
