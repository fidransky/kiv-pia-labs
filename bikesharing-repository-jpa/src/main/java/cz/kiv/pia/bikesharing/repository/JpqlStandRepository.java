package cz.kiv.pia.bikesharing.repository;

import cz.kiv.pia.bikesharing.domain.Stand;
import cz.kiv.pia.bikesharing.repository.dto.StandDTO;
import cz.kiv.pia.bikesharing.repository.mapper.StandMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collection;
import java.util.function.Function;

//@Primary
//@Repository
public class JpqlStandRepository implements StandRepository {
    @PersistenceContext
    private EntityManager em;

    private final Function<StandDTO, Stand> standMapper = new StandMapper();

    @Override
    public Collection<Stand> getAll() {
        var jpql = """
                SELECT s FROM Stand s
                """;

        var query = em.createQuery(jpql, StandDTO.class);

        var result = query.getResultList();

        return result.stream()
                .map(standMapper)
                .toList();
    }

    @Override
    public Collection<Stand> getAllByName(String q) {
        var jpql = """
                SELECT s FROM Stand s WHERE s.name LIKE :q
                """;

        var query = em.createQuery(jpql, StandDTO.class);
        query.setParameter("q", "%" + q + "%");

        var result = query.getResultList();

        return result.stream()
                .map(standMapper)
                .toList();
    }
}
