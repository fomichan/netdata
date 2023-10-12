package com.fomich.netdata.database.repository;

import com.fomich.netdata.database.entity.Multiplexer;
import com.fomich.netdata.database.entity.Site;
import com.fomich.netdata.dto.MultiplexerReadDto;
import com.fomich.netdata.dto.MultiplexerReadDto2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// 1) PartTreeJpaQuery - на основе именования методов создаются запросы (extends интерфейс JpaRepository с реализацией SimpleJpaRepository)

// 2) NamedQuery - исп функционал именованных запросов JPA
// Создаются через аннотацию @NamedQuуry над entity
// Имеет приоритет над PartTreeJpaQuery если совпадают названия

// 3) NativeJpaQuery и SimpleJpaQuery используют аннотацию @Query
// Устанавливается в параметре nativeQuery = true тогда NativeJpaQuery (на SQL)
// SimpleJpaQuery - исп. HQL, NativeJpaQuery - нативный SQL


public interface MultiplexerRepository extends JpaRepository<Multiplexer, Integer>,
        FilterMultiplexerRepository,
        RevisionRepository<Multiplexer, Integer, Integer>,
        QuerydslPredicateExecutor<Multiplexer> { // В нем уже есть Page<T> findAll(Predicate predicate, Pageable pageable);




    // Query(name = 'Multiplexer.findByName') //ссылаемся на NamedQuery
    @Query("select m from Multiplexer m join m.multiplexerChannels mc where m.name = :name2") // fetch не срабатывает
    Optional<Multiplexer> findByName(@Param("name2") String name);
    // может вернуть Optional, Entity, Future


//      SimpleJpaQuery
//    @Query("select u from User u " +
//            "where u.firstname like %:firstname% and u.lastname like %:lastname%")
//    List<User> findAllBy(String firstname, String lastname);
//
//      NativeJpaQuery
//    @Query(value = "SELECT u.* FROM users u WHERE u.username = :username",
//            nativeQuery = true)
//    List<User> findAllByUsername(String username);



    // разрешит Query выполнять не только Select, но и другие dml и ddl операции с изменением таблиц.
    // flushAutomaticaly - если мы изменили сущности до update запроса, то при clearAutomaticaly контекст
    //                      почистится и изменения потеряются. А эта настройка будет делать flush сессии автоматически.
    // Но в Hibernate flush происходит автоматом, если не отключить.
    // clearAutomaticaly - чистит PersistenceContext после выполнения modifying query. Те делается update, затем чистится весь PersistenceContext
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Multiplexer m set m.site = :site where m.id in (:ids)")
    int updateSite(Site site, Integer... ids);



    // Вместо
    // List<User> findTop3ByBirthDateBeforeOrderByBirthDateDesc(LocalDate birthDate);
    // можно передать объект сортировки (динамическая сортировка)
    // List<User> findTop3ByBirthDateBefore(LocalDate birthDate, Sort sort);6

    List<Multiplexer> findAllByNameContainingIgnoreCase(String fragment, Sort sort);


    List<Multiplexer> findAllByNameContainingIgnoreCase(String fragment);
    // может вернуть Collection, Stream (batch, close)




    // @EntityGraph(attributePaths = {"company", "company.locales"}) // можно сразу и subgraph
    // @EntityGraph(attributePaths = {"module"}) // EntityGraph будет создан динамически (не надо его создавать над Entity @NamedEntityGraph).
                                                    // Будут подтянуты сущности даже в случае Lazy (запрос сразу с left join/). Но вместо этого можно и в @Query указать fetch

    Page<Multiplexer> findAllBy(Pageable pageable);
    // Можно передать объект Pageable (указывают limit, offset и сортировку)
    // Можно использовать возвращаемые объекты Collection, Stream
    // Но с pageable можно использовать еще Streamable, Slice, Page, которые дают доп методы
    // Преимущество slice что мы можем получить следующий offset с тем же лимитом. В slice не узнать сколько всего страниц.
    // Когда нужно знать количество страниц можно использовать Page, в остальном такой же как slice


    // EntityGraph - в случае Fetch все связи будут подтянуты как Lazy, и только те которые указаны с помощью Graph будут подтянуты как Eager
    //               в случае Load все связи останутся как прописаны в Entity, была Lazy и останется Lazy, была Eager и останется Eager
    //@EntityGraph("User.company") // Указываем какой Entity graph используем (указываются в аннотации @NamedEntityGraph над Entity)
    // Здесь будет подтянут даже субграф company.locales. В именованном графе это будет громоздко

    // Но с EntityGraph Page будет работать не правильно, т.к. не сможет делать limit и offset, и будет доставать всех пользователей, а уже потом отсекать.
    // Поэтому будет либо ошибка либо плохой перформанс



//    @Query(value = "select u from User u",
//            countQuery = "select count(distinct u.firstname) from User u") // можно в Query изменить запросы для Page
//    @QueryHints(@QueryHint(name = "org.hibernate.fetchSize", value = "50")) // дополнительные оптимизации к запросу: таймаут, кеширование и тп







//    void delete(Multiplexer entity);


    @Query(value = "SELECT name, " +
            "serial_number " +
            "FROM multiplexer " +
            "WHERE site_id = :siteId",
            nativeQuery = true)
    List<MultiplexerReadDto2> findAllBySiteId(Integer siteId);


}
