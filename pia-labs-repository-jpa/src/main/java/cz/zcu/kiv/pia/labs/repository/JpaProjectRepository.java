package cz.zcu.kiv.pia.labs.repository;

import cz.zcu.kiv.pia.labs.domain.Project;
import cz.zcu.kiv.pia.labs.repository.entity.ProjectDTO;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class JpaProjectRepository implements ProjectRepository {

    private final JpaRepository<ProjectDTO, UUID> projectRepository;

    public JpaProjectRepository(JpaRepository<ProjectDTO, UUID> projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void store(Project project) {
        // TODO: implement this
    }

    @Override
    public List<Project> getAll() {
        var result = projectRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));

        return result.stream()
                .map(projectDTO -> {
                    var projectId = projectDTO.getId();
                    // TODO: map other fields

                    return new Project(projectId);
                })
                .toList();
    }

    @Override
    public Project findById(UUID id) {
        var result = projectRepository.findById(id);

        return result
                .map(projectDTO -> {
                    var projectId = projectDTO.getId();
                    // TODO: map other fields

                    return new Project(projectId);
                })
                .orElse(null);
    }
}
