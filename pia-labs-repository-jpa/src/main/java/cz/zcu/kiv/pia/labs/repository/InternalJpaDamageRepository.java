package cz.zcu.kiv.pia.labs.repository;

import cz.zcu.kiv.pia.labs.repository.entity.DamageDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface InternalJpaDamageRepository extends JpaRepository<DamageDTO, UUID> {
}
