package profeel.taraboss.DTO;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import profeel.taraboss.Entity.Role;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String email ;
    private String password ;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Date createdAt;


}
