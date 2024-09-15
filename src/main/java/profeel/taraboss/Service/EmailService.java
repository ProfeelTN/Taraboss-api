package profeel.taraboss.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import profeel.taraboss.DTO.Mail;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {

    private final JavaMailSender javaMailSender;
    @Override
    public void sendMail(Mail mail) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        String htmlContent = mail.getBody();
        helper.setText(htmlContent, true);
        helper.setFrom("postmaster@profeel.tn");
        helper.setTo(mail.getTo());
        helper.setSubject(mail.getSubject());
        javaMailSender.send(mimeMessage);
    }
}