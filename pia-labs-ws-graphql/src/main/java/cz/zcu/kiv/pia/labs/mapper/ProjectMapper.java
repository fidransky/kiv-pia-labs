package cz.zcu.kiv.pia.labs.mapper;

import cz.zcu.kiv.pia.labs.domain.Project;
import cz.zcu.kiv.pia.labs.domain.User;
import cz.zcu.kiv.pia.labs.graphql.ProjectDTO;
import cz.zcu.kiv.pia.labs.graphql.ProjectStateDTO;
import cz.zcu.kiv.pia.labs.graphql.UserDTO;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.util.Base64;

@Component
public class ProjectMapper {

    public ProjectDTO toDTO(Project project) {
        return ProjectDTO.builder()
                .withId(project.getId())
                .withCustomer(toUserDTO(project.getCustomer()))
                .withTranslator(project.getTranslator() != null ? toUserDTO(project.getTranslator()) : null)
                .withTargetLanguage(project.getTargetLanguage().toLanguageTag())
                .withSourceFile(Base64.getEncoder().encodeToString(project.getSourceFile()))
                .withTranslatedFile(project.getTranslatedFile() != null ? Base64.getEncoder().encodeToString(project.getTranslatedFile()) : null)
                .withState(ProjectStateDTO.valueOf(project.getState().toString()))
                .withCreatedAt(project.getCreatedAt().atOffset(ZoneOffset.UTC))
                .build();
    }

    private UserDTO toUserDTO(User user) {
        return UserDTO.builder()
                .withId(user.getId())
                .withName(user.getName())
                .withEmailAddress(user.getEmailAddress())
                .build();
    }
}
