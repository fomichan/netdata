package com.fomich.netdata.database.repository;

import com.fomich.netdata.database.entity.Channel;
import com.fomich.netdata.database.entity.Module;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModuleRepository extends
        JpaRepository<Module, Integer> {



    List<Integer> findSlotByMultiplexerId(Integer multiplexerId);


    // Для валидации уникальных слотов в мультиплексоре
    @Query("SELECT m.slot FROM Module m WHERE m.multiplexer.id = :multiplexerId")
    List<Tuple> findModuleSlotByMultiplexerId(@Param("multiplexerId") Integer multiplexerId);

    // 2 вариант Для валидации уникальных слотов в мультиплексоре
    //List<Module> findAllByMultiplexerId(Integer multiplexerId);






}
