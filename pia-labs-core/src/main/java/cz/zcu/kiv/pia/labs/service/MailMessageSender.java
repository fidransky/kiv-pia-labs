package cz.zcu.kiv.pia.labs.service;

import cz.zcu.kiv.pia.labs.domain.Project;
import org.slf4j.Logger;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Implementation of MessageSender interface that sends messages via email.
 */
@Service
public class MailMessageSender implements MessageSender {
    private static final Logger LOG = getLogger(MailMessageSender.class);

    private final JavaMailSender mailSender;

    public MailMessageSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendProjectCompletedMessage(Project project) {
        LOG.info("Sending email about completed project {} to customer {}", project.getId(), project.getCustomer().getId());

        var projectUrl = "http://localhost:8080/project/%s".formatted(project.getId());

        var subject = "Project is completed";
        var text = """
                Hello %s,
                
                Your translation project was successfully completed by %s.
                
                Download translated documents here: %s
                """.formatted(project.getCustomer().getName(), project.getTranslator().getName(), projectUrl);

        var message = new SimpleMailMessage();
        message.setFrom("pia@kiv.zcu.cz");
        message.setTo(project.getCustomer().getEmailAddress());
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
}
