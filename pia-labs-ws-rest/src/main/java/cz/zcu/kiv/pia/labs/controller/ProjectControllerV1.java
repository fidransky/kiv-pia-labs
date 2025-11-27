package cz.zcu.kiv.pia.labs.controller;

import cz.zcu.kiv.pia.labs.model.CreateProjectRequestDTO;
import cz.zcu.kiv.pia.labs.model.CreateProjectResponseDTO;
import cz.zcu.kiv.pia.labs.service.ProjectService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.Locale;

@RestController
@RequestMapping(path = "/v1")
public class ProjectControllerV1 implements ProjectsApi {
    private final ProjectService projectService;

    public ProjectControllerV1(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public ResponseEntity<CreateProjectResponseDTO> createProject(CreateProjectRequestDTO createProjectRequestDTO) {
        try {
            // map input
            var targetLanguage = Locale.forLanguageTag(createProjectRequestDTO.getLanguageCode());
            var sourceFile = createProjectRequestDTO.getOriginalFile().getContentAsByteArray();

            // call core service
            var createdProject = projectService.createProject(targetLanguage, sourceFile);

            // map output
            var createdProjectDTO = new CreateProjectResponseDTO()
                    .id(createdProject.getId())
                    .languageCode(createdProject.getTargetLanguage().toLanguageTag())
                    .originalFile(new ByteArrayResource(createdProject.getSourceFile()))
                    .state(CreateProjectResponseDTO.StateEnum.valueOf(createdProject.getState().toString()))
                    .createdAt(createdProject.getCreatedAt().atOffset(ZoneOffset.UTC));

            return new ResponseEntity<>(createdProjectDTO, HttpStatus.CREATED);

        } catch (IOException e) {
            // TODO: log the exception

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            // TODO: log the exception

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
