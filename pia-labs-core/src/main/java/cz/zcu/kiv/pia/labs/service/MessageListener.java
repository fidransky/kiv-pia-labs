package cz.zcu.kiv.pia.labs.service;

import cz.zcu.kiv.pia.labs.event.ProjectCompletedEvent;
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

    @EventListener(ProjectCompletedEvent.class)
    public void onProjectCompletedEvent(ProjectCompletedEvent event) {
        LOG.info("Received event about completed project {}", event.getProject().getId());

        var completedProject = event.getProject();
        messageSender.sendProjectCompletedMessage(completedProject);
    }
}
