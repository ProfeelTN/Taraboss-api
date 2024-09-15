package profeel.taraboss.DTO;

import lombok.*;
import profeel.taraboss.Entity.Offre;
import profeel.taraboss.Entity.UserProfile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OffreUserProfileDTO {
    private Offre offre;
    private UserProfile userProfile;
}
