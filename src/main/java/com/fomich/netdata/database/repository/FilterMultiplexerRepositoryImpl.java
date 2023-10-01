package com.fomich.netdata.database.repository;

import com.fomich.netdata.database.entity.Multiplexer;
import com.fomich.netdata.dto.MultiplexerFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FilterMultiplexerRepositoryImpl implements FilterMultiplexerRepository{

    private final EntityManager entityManager;


    @Override
    public List<Multiplexer> findAllByFilter(MultiplexerFilter filter) {

        // Реализуем на Criteria API
        var cb = entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(Multiplexer.class);

        var multiplexer = criteria.from(Multiplexer.class);
        criteria.select(multiplexer);

        List<Predicate> predicates = new ArrayList<>();
        if (filter.name() != null) {
            predicates.add(cb.like(multiplexer.get("name"), filter.name()));
        }
//        if (filter.serialNumber() != null) {
//            predicates.add(cb.like(multiplexer.get("serialNumber"), filter.serialNumber()));
//        }
//        if (filter.birthDate() != null) {
//            predicates.add(cb.lessThan(user.get("birthDate"), filter.birthDate()));
//        }
        criteria.where(predicates.toArray(Predicate[]::new));

        return entityManager.createQuery(criteria).getResultList();
    }
}
