package cz.zcu.kiv.pia.labs.resolver;

import cz.zcu.kiv.pia.labs.graphql.ProjectDTO;
import cz.zcu.kiv.pia.labs.graphql.UserDTO;
import cz.zcu.kiv.pia.labs.mapper.ProjectMapper;
import cz.zcu.kiv.pia.labs.service.ProjectService;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    @BatchMapping(typeName = "ProjectDTO", field = "customer")
    public Map<ProjectDTO, UserDTO> loadCustomersForProjects(List<ProjectDTO> projects) {
        // TODO: load customers for given projects in a better optimized way
        return Collections.emptyMap();
    }
}
