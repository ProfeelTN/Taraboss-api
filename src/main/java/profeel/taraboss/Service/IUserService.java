package profeel.taraboss.Service;

import profeel.taraboss.DTO.ForgetPassword;
import profeel.taraboss.DTO.UserDTO;
import profeel.taraboss.DTO.resetPasswordRequest;
import profeel.taraboss.Entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<UserDTO> findAll();

    Optional<User> findById(Long id);

    void deleteById(Long id);

    Optional<User> getCurrentUser(String token);

    void resetPassword(resetPasswordRequest request);

    void forgetPassword(ForgetPassword forgetPassword) throws jakarta.mail.MessagingException;
}
