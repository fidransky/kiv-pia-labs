package cz.zcu.kiv.pia.labs.repository;

import cz.zcu.kiv.pia.labs.domain.Damage;
import cz.zcu.kiv.pia.labs.domain.Location;
import cz.zcu.kiv.pia.labs.domain.User;
import cz.zcu.kiv.pia.labs.repository.entity.DamageDTO;
import cz.zcu.kiv.pia.labs.repository.entity.LocationDTO;
import cz.zcu.kiv.pia.labs.repository.entity.UserDTO;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Primary
@Repository
public class JpaDamageRepository implements DamageRepository {

    private final JpaRepository<DamageDTO, UUID> damageRepository;

    public JpaDamageRepository(JpaRepository<DamageDTO, UUID> damageRepository) {
        this.damageRepository = damageRepository;
    }

    @Override
    public Damage create(Damage damage) {
        var damageDTO = new DamageDTO(damage.getId(), new UserDTO(damage.getInsured()), new UserDTO(damage.getImpaired()), damage.getTimestamp(), new LocationDTO(damage.getLocation()), damage.getDescription(), damage.getState());

        damageDTO = damageRepository.save(damageDTO);

        var id = damageDTO.getId();
        var insuredUser = new User(damageDTO.getInsuredUser().getId(), damageDTO.getInsuredUser().getName(), damageDTO.getInsuredUser().getEmailAddress(), damageDTO.getInsuredUser().getRole());
        var impairedUser = new User(damageDTO.getImpairedUser().getId(), damageDTO.getImpairedUser().getName(), damageDTO.getImpairedUser().getEmailAddress(), damageDTO.getImpairedUser().getRole());
        var timestamp = damageDTO.getTimestamp();
        var location = new Location(damageDTO.getLocation().getLongitude(), damageDTO.getLocation().getLatitude());
        var description = damageDTO.getDescription();
        var state = damageDTO.getState();

        return new Damage(id, insuredUser, impairedUser, timestamp, location, description, state);
    }

    @Override
    public Collection<Damage> retrieveReportedDamage(User user) {
        var result = damageRepository.findAll();

        return result.stream()
                .map(damageDTO -> {
                    var id = damageDTO.getId();
                    var insuredUser = new User(damageDTO.getInsuredUser().getId(), damageDTO.getInsuredUser().getName(), damageDTO.getInsuredUser().getEmailAddress(), damageDTO.getInsuredUser().getRole());
                    var impairedUser = new User(damageDTO.getImpairedUser().getId(), damageDTO.getImpairedUser().getName(), damageDTO.getImpairedUser().getEmailAddress(), damageDTO.getImpairedUser().getRole());
                    var timestamp = damageDTO.getTimestamp();
                    var location = new Location(damageDTO.getLocation().getLongitude(), damageDTO.getLocation().getLatitude());
                    var description = damageDTO.getDescription();
                    var state = damageDTO.getState();

                    return new Damage(id, insuredUser, impairedUser, timestamp, location, description, state);
                })
                .toList();
    }
}
