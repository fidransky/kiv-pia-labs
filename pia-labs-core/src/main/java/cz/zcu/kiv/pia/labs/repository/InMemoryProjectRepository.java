package cz.zcu.kiv.pia.labs.repository;

import cz.zcu.kiv.pia.labs.domain.Project;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryProjectRepository implements ProjectRepository {
    private final Map<UUID, Project> projects = new ConcurrentHashMap<>();

    @Override
    public void store(Project project) {
        projects.put(project.getId(), project);
    }

    @Override
    public Project findById(UUID id) {
        return projects.get(id);
    }
}
