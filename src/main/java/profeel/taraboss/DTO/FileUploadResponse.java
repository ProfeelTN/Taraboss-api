package profeel.taraboss.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FileUploadResponse {
    private String imagePath;
    private String resumePath;
    private LocalDateTime dateTime;
}
