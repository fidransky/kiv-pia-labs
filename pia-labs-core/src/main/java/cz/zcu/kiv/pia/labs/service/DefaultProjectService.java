package cz.zcu.kiv.pia.labs.service;

import cz.zcu.kiv.pia.labs.domain.Project;
import cz.zcu.kiv.pia.labs.domain.User;
import cz.zcu.kiv.pia.labs.event.ProjectCompletedEvent;
import cz.zcu.kiv.pia.labs.repository.ProjectRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Transactional(readOnly = true)
public class DefaultProjectService implements ProjectService {
    private final UserService userService;
    private final ProjectRepository projectRepository;
    private final ApplicationEventPublisher eventPublisher;

    public DefaultProjectService(UserService userService, ProjectRepository projectRepository, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.projectRepository = projectRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional(readOnly = false)
    @Secured("ROLE_CUSTOMER")
    //@PreAuthorize("hasRole('CUSTOMER')")
    @Override
    public Project createProject(Locale targetLanguage, byte[] sourceFile) {
        User currentUser = userService.getCurrentUser();

        Project project = currentUser.createProject(targetLanguage, sourceFile);

        projectRepository.store(project);

        return project;
    }

    // TODO: check that user has ADMINISTRATOR role (i.e. authorization)
    @Override
    public List<Project> getAllProjects() {
        List<Project> projects = projectRepository.getAll();

        return projects;
    }

    @Override
    public Project getProjectById(UUID id) {
        Project project = projectRepository.findById(id);

        return Optional.ofNullable(project).orElseThrow();
    }

    // TODO: check that user has TRANSLATOR role (i.e. authorization)
    @Transactional
    @Override
    public void completeProject(UUID id, byte[] translatedFile) {
        Project project = projectRepository.findById(id);
        if (project == null) {
            throw new NoSuchElementException();
        }

        User user = project.getTranslator();
        user.completeProject(project, translatedFile);

        projectRepository.store(project);

        // publish application event
        eventPublisher.publishEvent(new ProjectCompletedEvent(project));
    }
}
