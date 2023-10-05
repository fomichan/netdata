package com.fomich.netdata.database.repository;

import com.fomich.netdata.database.entity.Multiplexer;
import com.fomich.netdata.dto.MultiplexerFilter;
import com.fomich.netdata.dto.MultiplexerReadDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FilterMultiplexerRepositoryImpl implements FilterMultiplexerRepository{

    private final EntityManager entityManager;

    private final JdbcTemplate jdbcTemplate;


    private static final String FIND_BY_CHANNEL = """
        SELECT 
            id,
            name,
            serial_number            
        FROM multiplexer 
        WHERE site_id = ?                   
    """;


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



    // С использованием JDBC
//    @Override
//    public List<MultiplexerReadDto> findAllByChannelId(Integer channelId) {
//        return jdbcTemplate.query(FIND_BY_CHANNEL,
//                (rs, rowNum) -> new MultiplexerReadDto(
//                        rs.getString("name"),
//                        rs.getInt("serial_number")
//
//                ), channelId);
//    }
}
