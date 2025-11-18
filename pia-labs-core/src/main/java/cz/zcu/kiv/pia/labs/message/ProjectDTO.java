package cz.zcu.kiv.pia.labs.message;

import cz.zcu.kiv.pia.labs.domain.Project;

import java.io.Serializable;
import java.util.UUID;

/**
 * Encapsulates a subset of fields of the {@code Project} entity for controlled data transfer.
 */
public record ProjectDTO(UUID id) implements Serializable {
    public ProjectDTO(Project project) {
        this(project.getId());
    }
}
