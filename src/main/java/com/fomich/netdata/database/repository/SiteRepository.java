package com.fomich.netdata.database.repository;

import com.fomich.netdata.database.entity.Site;
import com.fomich.netdata.dto.SiteShortReadDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface SiteRepository extends JpaRepository<Site, Integer>,
        QuerydslPredicateExecutor<Site> {

    Optional<Site> findById(Integer id);

    //List<SiteIdAndNameProjections> findAllBy(); // Не использую. Чтобы читать только ID и Name для отображения

    //List<SiteShortReadDto> findAllByIdAndName();

    List<SiteShortReadDto> findAllBy();

    // Для валидации
    Optional<Site> findByNameIgnoreCase(String name);


}
