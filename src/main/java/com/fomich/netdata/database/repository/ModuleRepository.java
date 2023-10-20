package com.fomich.netdata.database.repository;

import com.fomich.netdata.database.entity.Channel;
import com.fomich.netdata.database.entity.Module;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ModuleRepository extends
        JpaRepository<Module, Integer> {


}
