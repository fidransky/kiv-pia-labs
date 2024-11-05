package cz.zcu.kiv.pia.labs.service;

import cz.zcu.kiv.pia.labs.domain.Damage;
import cz.zcu.kiv.pia.labs.event.DamageReportedEvent;
import org.slf4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class MessageListener {
    private static final Logger LOG = getLogger(MessageListener.class);

    private final MessageSender messageSender;
    private final JmsTemplate jmsTemplate;

    public MessageListener(MessageSender messageSender, JmsTemplate jmsTemplate) {
        this.messageSender = messageSender;
        this.jmsTemplate = jmsTemplate;
    }

    @EventListener
    public void listenForReportedDamageEvent(DamageReportedEvent event) {
        LOG.info("Received event about newly reported damage {}", event.damage());

        var reportedDamage = event.damage();
        var destination = "kiv.pia.labs.damage.%s".formatted(reportedDamage.getId());

        jmsTemplate.convertAndSend(destination, reportedDamage);
    }

    @JmsListener(destination = "kiv.pia.labs.damage.*")
    public void listenForReportedDamageMessage(Damage reportedDamage) {
        LOG.info("Received message about newly reported damage {}", reportedDamage);

        messageSender.sendReportedDamageMessage(reportedDamage.getImpaired(), reportedDamage.getId());
    }
}
