package cz.zcu.kiv.pia.labs.service;

import cz.zcu.kiv.pia.labs.domain.Project;
import cz.zcu.kiv.pia.labs.domain.User;
import cz.zcu.kiv.pia.labs.repository.ProjectRepository;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public class DefaultProjectService implements ProjectService {
    private final UserService userService;
    private final ProjectRepository projectRepository;

    public DefaultProjectService(UserService userService, ProjectRepository projectRepository) {
        this.userService = userService;
        this.projectRepository = projectRepository;
    }

    @Override
    public Project createProject(Locale targetLanguage, byte[] sourceFile) {
        User currentUser = userService.getCurrentUser();

        Project project = currentUser.createProject(targetLanguage, sourceFile);

        projectRepository.store(project);

        return project;
    }

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

    @Override
    public void completeProject(UUID id, byte[] translatedFile) {
        Project project = projectRepository.findById(id);
        if (project == null) {
            throw new NoSuchElementException();
        }

        User user = project.getTranslator();
        user.completeProject(project, translatedFile);

        projectRepository.store(project);
    }
}
