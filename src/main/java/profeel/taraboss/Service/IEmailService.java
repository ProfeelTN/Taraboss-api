package profeel.taraboss.Service;

import jakarta.mail.MessagingException;
import profeel.taraboss.DTO.Mail;

public interface IEmailService {
    void sendMail(Mail mail) throws MessagingException;
}
