package cz.zcu.kiv.pia.labs.service;

import cz.zcu.kiv.pia.labs.domain.User;
import org.slf4j.Logger;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class MailMessageSender implements MessageSender {
    private static final Logger LOG = getLogger(MailMessageSender.class);

    private final JavaMailSender mailSender;

    public MailMessageSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendReportedDamageMessage(User impaired, UUID damageId) {
        LOG.info("Sending email about newly reported damage with ID {} to impaired user {}", damageId, impaired);

        var damageUrl = "http://localhost/damage/%s".formatted(damageId);

        var subject = "New reported damage";
        var text = """
                Hello %s,
                
                There is a new damage reported where you are listed as an impaired party.
                
                Upload relevant documents here: %s
                """.formatted(impaired.getName(), damageUrl);

        var message = new SimpleMailMessage();
        message.setFrom("pia@kiv.zcu.cz");
        message.setTo(impaired.getEmailAddress());
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
}
