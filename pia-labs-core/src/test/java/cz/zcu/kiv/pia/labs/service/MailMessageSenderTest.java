package cz.zcu.kiv.pia.labs.service;

import cz.zcu.kiv.pia.labs.domain.Damage;
import cz.zcu.kiv.pia.labs.domain.User;
import cz.zcu.kiv.pia.labs.domain.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentCaptor.captor;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MailMessageSenderTest {
    @Mock
    private JavaMailSender mailSender;
    @InjectMocks
    private MailMessageSender messageSender;

    @Test
    void sendReportedDamageMessage() {
        // test data
        var impaired = new User(UUID.randomUUID(), "Jane Doe", "jane.doe@example.com", UserRole.IMPAIRED);
        var damage = new Damage(UUID.randomUUID());

        ArgumentCaptor<SimpleMailMessage> messageCaptor = captor();

        // tested method
        messageSender.sendReportedDamageMessage(impaired, damage.getId());

        // assertions
        verify(mailSender).send(messageCaptor.capture());

        assertEquals("New reported damage", messageCaptor.getValue().getSubject());
        assertEquals(1, messageCaptor.getValue().getTo().length);
        assertEquals(impaired.getEmailAddress(), messageCaptor.getValue().getTo()[0]);
    }
}