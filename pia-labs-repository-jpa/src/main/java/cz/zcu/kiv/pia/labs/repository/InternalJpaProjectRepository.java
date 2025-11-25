package cz.zcu.kiv.pia.labs.repository;

import cz.zcu.kiv.pia.labs.repository.entity.ProjectDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface InternalJpaProjectRepository extends JpaRepository<ProjectDTO, UUID> {
}
