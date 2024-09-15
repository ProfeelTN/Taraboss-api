package profeel.taraboss.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import profeel.taraboss.Entity.Type;

import java.text.DateFormat;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OffreDTO {

    private Long id;
    private String title;
    private String description;
    private Long duration;
    private String company;
    private String Location;
    private String keywords;
    private Type type;
    private Date createdAt;
    private int candidatureCount;

}
