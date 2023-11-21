package cz.kiv.pia.bikesharing.repository;

import cz.kiv.pia.bikesharing.domain.Stand;
import cz.kiv.pia.bikesharing.repository.dto.StandDTO;
import cz.kiv.pia.bikesharing.repository.mapper.StandMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

//@Primary
//@Repository
public interface JpaStandRepository extends StandRepository, JpaRepository<StandDTO, UUID> {

    Function<StandDTO, Stand> standMapper = new StandMapper();

    @Override
    default Collection<Stand> getAll() {
        var result = findAll();

        return result.stream()
                .map(standMapper)
                .toList();
    }

    @Override
    default Collection<Stand> getAllByName(String q) {
        var result = findAllByNameContaining("%" + q + "%");

        return result.stream()
                .map(standMapper)
                .toList();
    }

    /**
     * Method using Spring Data query derivation mechanism to derive SQL query from method name.
     *
     * @param q Partial stand name
     * @return Stands matching given name
     */
    @Query("SELECT s FROM Stand s WHERE s.name LIKE :query")
    List<StandDTO> findAllByNameContaining(@Param("query") String q);
}
