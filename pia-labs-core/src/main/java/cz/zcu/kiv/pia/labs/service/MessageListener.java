package cz.zcu.kiv.pia.labs.service;

import cz.zcu.kiv.pia.labs.event.ProjectCompletedEvent;
import cz.zcu.kiv.pia.labs.message.ProjectDTO;
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
    private final ProjectService projectService;
    private final JmsTemplate jmsTemplate;

    public MessageListener(MessageSender messageSender, ProjectService projectService, JmsTemplate jmsTemplate) {
        this.messageSender = messageSender;
        this.projectService = projectService;
        this.jmsTemplate = jmsTemplate;
    }

    @EventListener(ProjectCompletedEvent.class)
    public void onProjectCompletedEvent(ProjectCompletedEvent event) {
        LOG.info("Received event about completed project {}", event.getProject().getId());

        var completedProject = event.getProject();
        var destination = "kiv.pia.labs.project.%s".formatted(completedProject.getId());
        jmsTemplate.convertAndSend(destination, new ProjectDTO(completedProject));
    }

    @JmsListener(destination = "kiv.pia.labs.project.*")
    public void listenForCompletedProjectMessage(ProjectDTO projectDTO) {
        LOG.info("Received message about completed project {}", projectDTO.id());

        var completedProject = projectService.getProjectById(projectDTO.id());
        messageSender.sendProjectCompletedMessage(completedProject);
    }
}
