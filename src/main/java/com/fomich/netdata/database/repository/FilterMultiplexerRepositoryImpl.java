package com.fomich.netdata.database.repository;

import com.fomich.netdata.database.entity.Multiplexer;
import com.fomich.netdata.database.entity.QMultiplexer;
import com.fomich.netdata.database.querydsl.QPredicates;
import com.fomich.netdata.dto.MultiplexerFilter;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

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



    /*
    // Реализуем на Criteria API
    @Override
    public List<Multiplexer> findAllByFilter(MultiplexerFilter filter) {


        var cb = entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(Multiplexer.class);

        var multiplexer = criteria.from(Multiplexer.class);
        criteria.select(multiplexer);

        List<Predicate> predicates = new ArrayList<>();
        if (filter.name() != null) {
            predicates.add(cb.like(multiplexer.get("name"), "%" + filter.name() + "%"));
        }
        if (filter.siteName() != null) {
            predicates.add(cb.like(multiplexer.get("site").get("name"), "%" + filter.siteName() + "%"));
        }
        if (filter.serialNumber() != null) {
            predicates.add(cb.like(multiplexer.get("serialNumber").as(String.class), "%" + filter.serialNumber() + "%")); // .as(String.class) чтобы воспринимал как строки
        }
//        if (filter.birthDate() != null) {
//            predicates.add(cb.lessThan(user.get("birthDate"), filter.birthDate()));
//        }
        criteria.where(predicates.toArray(Predicate[]::new));

        List<Multiplexer> resultList = entityManager.createQuery(criteria).getResultList();
        return resultList;
        //return entityManager.createQuery(criteria).getResultList();
    }

     */




    // Реализуем на Querydsl
    @Override
    public List<Multiplexer> findAllByFilter(MultiplexerFilter filter) {
        var predicate = QPredicates.builder()
                .add(filter.name(), QMultiplexer.multiplexer.name::containsIgnoreCase)
                //.add(filter.serialNumber(), QMultiplexer.multiplexer.serialNumber::containsIgnoreCase)
                .build();

        return new JPAQuery<Multiplexer>(entityManager)
                .select(QMultiplexer.multiplexer)
                .from(QMultiplexer.multiplexer)
                .where(predicate)
                .fetch();
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
