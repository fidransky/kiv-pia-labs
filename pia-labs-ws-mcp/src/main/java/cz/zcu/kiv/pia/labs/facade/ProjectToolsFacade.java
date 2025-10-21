package cz.zcu.kiv.pia.labs.facade;

import cz.zcu.kiv.pia.labs.service.ProjectService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@Service
public class ProjectToolsFacade {
    private final ProjectService projectService;

    public ProjectToolsFacade(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Tool(name = "create-translation-project", description = "Create a new translation project")
    public Map<String, Object> createProject(
            @ToolParam(description = "Target language as ISO-2", required = true) String targetLanguage,
            @ToolParam(description = "Source file") String sourceFileBase64
    ) {
        Assert.hasLength(targetLanguage, "Target language must be specified");
        Assert.hasLength(sourceFileBase64, "Source file must be specified");

        Locale locale = Locale.forLanguageTag(targetLanguage);
        byte[] sourceFile = Base64.getDecoder().decode(sourceFileBase64);

        var project = projectService.createProject(locale, sourceFile);

        return Map.of(
                "projectId", project.getId(),
                "state", project.getState(),
                "createdAt", project.getCreatedAt().toString()
        );
    }

    @Tool(name = "get-all-translation-projects", description = "Get all translation projects")
    public Map<String, Object> getProjects() {
        var projects = projectService.getAllProjects();

        var projectList = projects.stream()
                .map(project -> Map.of(
                        "projectId", project.getId().toString(),
                        "state", project.getState().toString(),
                        "createdAt", project.getCreatedAt().toString()
                ))
                .toList();

        return Map.of(
                "total", projectList.size(),
                "projects", projectList,
                "retrievedAt", LocalDateTime.now().toString()
        );
    }

    @Tool(name = "get-translation-project", description = "Get translation project details")
    public Map<String, Object> getProject(
            @ToolParam(description = "Translation project identifier") UUID projectId
    ) {
        var project = projectService.getProjectById(projectId);

        return Map.of(
                "projectId", project.getId(),
                "state", project.getState(),
                "createdAt", project.getCreatedAt().toString()
        );
    }
}
