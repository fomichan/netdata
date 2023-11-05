package com.fomich.netdata.database.repository;

import com.fomich.netdata.database.entity.Multiplexer;
import com.fomich.netdata.database.entity.Site;
import com.fomich.netdata.database.entity.User;
import com.fomich.netdata.dto.SiteShortReadDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User>{

   Optional<User> findByUsername(String username);

   // Для валидации
   Optional<User> findByUsernameIgnoreCase(String username);


}
