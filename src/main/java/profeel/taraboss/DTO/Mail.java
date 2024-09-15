package profeel.taraboss.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mail {
    private String to;
    private String subject;
    private String body;
}
