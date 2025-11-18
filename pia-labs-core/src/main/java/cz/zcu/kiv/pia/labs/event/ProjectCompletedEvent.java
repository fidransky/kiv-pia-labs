package cz.zcu.kiv.pia.labs.event;

import cz.zcu.kiv.pia.labs.domain.Project;

import java.util.Objects;

/**
 * Event published when a project is completed.
 */
public class ProjectCompletedEvent {
    private final Project project;

    public ProjectCompletedEvent(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProjectCompletedEvent that)) return false;
        return Objects.equals(project, that.project);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(project);
    }

    @Override
    public String toString() {
        return "ProjectCompletedEvent{" +
                "project=" + project +
                '}';
    }
}
