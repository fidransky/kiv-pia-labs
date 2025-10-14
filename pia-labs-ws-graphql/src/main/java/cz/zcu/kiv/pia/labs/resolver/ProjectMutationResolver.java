package cz.zcu.kiv.pia.labs.resolver;

import cz.zcu.kiv.pia.labs.graphql.ProjectDTO;
import cz.zcu.kiv.pia.labs.mapper.ProjectMapper;
import cz.zcu.kiv.pia.labs.service.ProjectService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.util.Base64;
import java.util.Locale;
import java.util.Map;

@Controller
public class ProjectMutationResolver {

    private final ProjectService projectService;
    private final ProjectMapper projectMapper;

    public ProjectMutationResolver(ProjectService projectService, ProjectMapper projectMapper) {
        this.projectService = projectService;
        this.projectMapper = projectMapper;
    }

    @MutationMapping
    public ProjectDTO createProject(@Argument Map<String, String> input) {
        String targetLanguage = input.get("targetLanguage");
        String sourceFileBase64 = input.get("sourceFile");

        Locale locale = Locale.forLanguageTag(targetLanguage);
        byte[] sourceFile = Base64.getDecoder().decode(sourceFileBase64);

        var project = projectService.createProject(locale, sourceFile);
        return projectMapper.toDTO(project);
    }
}
