package cz.zcu.kiv.pia.labs.service;

import cz.zcu.kiv.pia.labs.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Collections;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MailMessageSenderTest {
    @Mock
    private JavaMailSender javaMailSender;

    private MailMessageSender mailMessageSender;

    @BeforeEach
    void setUp() {
        this.mailMessageSender = new MailMessageSender(javaMailSender);
    }

    @Test
    void sendProjectCompletedMessage() {
        var translator = User.createTranslator("John Doe", "john.doe@example.com", Collections.singleton(Locale.FRENCH));
        var customer = User.createCustomer("Jane Doe", "jane.doe@example.com");
        var project = customer.createProject(Locale.FRENCH, "test content".getBytes());
        project.assignTranslator(translator);

        var messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        mailMessageSender.sendProjectCompletedMessage(project);

        verify(javaMailSender).send(messageCaptor.capture());

        assertNotNull(messageCaptor.getValue().getTo());
        assertEquals(1, messageCaptor.getValue().getTo().length);
        assertEquals(customer.getEmailAddress(), messageCaptor.getValue().getTo()[0]);
        assertEquals("Project is completed", messageCaptor.getValue().getSubject());
    }
}