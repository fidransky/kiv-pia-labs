package cz.zcu.kiv.pia.labs.service;

import cz.zcu.kiv.pia.labs.domain.Project;
import cz.zcu.kiv.pia.labs.domain.User;
import cz.zcu.kiv.pia.labs.repository.ProjectRepository;

import java.util.List;
import java.util.Locale;

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
}
