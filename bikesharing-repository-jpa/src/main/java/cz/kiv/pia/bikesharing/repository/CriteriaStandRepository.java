package cz.kiv.pia.bikesharing.repository;

import cz.kiv.pia.bikesharing.domain.Stand;
import cz.kiv.pia.bikesharing.repository.dto.StandDTO;
import cz.kiv.pia.bikesharing.repository.dto.StandDTO_;
import cz.kiv.pia.bikesharing.repository.mapper.StandMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

@Primary
@Repository
public class CriteriaStandRepository implements StandRepository {
    @PersistenceContext
    private EntityManager em;

    private final Function<StandDTO, Stand> standMapper = new StandMapper();

    @Override
    public Collection<Stand> getAll() {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(StandDTO.class);

        cq.from(StandDTO.class);

        var query = em.createQuery(cq);

        var result = query.getResultList();

        return result.stream()
                .map(standMapper)
                .toList();
    }

    @Override
    public Collection<Stand> getAllByName(String q) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(StandDTO.class);

        var root = cq.from(StandDTO.class);
        var predicates = new ArrayList<Predicate>();
        predicates.add(cb.like(root.get(StandDTO_.name), "%" + q + "%"));

        cq.where(predicates.toArray(new Predicate[0]));

        var query = em.createQuery(cq);

        var result = query.getResultList();

        return result.stream()
                .map(standMapper)
                .toList();
    }
}
