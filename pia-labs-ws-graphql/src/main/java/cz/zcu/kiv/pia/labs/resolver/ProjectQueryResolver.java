package cz.zcu.kiv.pia.labs.resolver;

import cz.zcu.kiv.pia.labs.graphql.ProjectDTO;
import cz.zcu.kiv.pia.labs.mapper.ProjectMapper;
import cz.zcu.kiv.pia.labs.service.ProjectService;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProjectQueryResolver {

    private final ProjectService projectService;
    private final ProjectMapper projectMapper;

    public ProjectQueryResolver(ProjectService projectService, ProjectMapper projectMapper) {
        this.projectService = projectService;
        this.projectMapper = projectMapper;
    }

    @QueryMapping
    public List<ProjectDTO> projects() {
        return projectService.getAllProjects().stream()
                .map(projectMapper::toDTO)
                .toList();
    }
}
