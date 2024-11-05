package cz.zcu.kiv.pia.labs.service;

import cz.zcu.kiv.pia.labs.event.DamageReportedEvent;
import org.slf4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class MessageListener {
    private static final Logger LOG = getLogger(MessageListener.class);

    private final MessageSender messageSender;

    public MessageListener(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @EventListener
    public void listenForReportedDamageEvent(DamageReportedEvent event) {
        LOG.info("Received event about newly reported damage {}", event.damage());

        var reportedDamage = event.damage();
        messageSender.sendReportedDamageMessage(reportedDamage.getImpaired(), reportedDamage.getId());
    }
}
