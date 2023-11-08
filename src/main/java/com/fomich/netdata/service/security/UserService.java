package com.fomich.netdata.service.security;

import com.fomich.netdata.database.querydsl.QPredicates;
import com.fomich.netdata.database.repository.UserRepository;
import com.fomich.netdata.dto.UserCreateEditDto;
import com.fomich.netdata.dto.UserFilter;
import com.fomich.netdata.dto.UserReadDto;
import com.fomich.netdata.mapper.UserCreateEditMapper;
import com.fomich.netdata.mapper.UserReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.fomich.netdata.database.entity.QUser.user;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;


    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    public Page<UserReadDto> findAll(UserFilter filter, Pageable pageable) {
        var predicate = QPredicates.builder()
                .add(filter.username(), user.firstname::containsIgnoreCase)
                .add(filter.firstname(), user.firstname::containsIgnoreCase)
                .add(filter.lastname(), user.lastname::containsIgnoreCase)
                .build();

        return userRepository.findAll(predicate, pageable)
                .map(userReadMapper::map);
    }



    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(userReadMapper::map)
                .toList();
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }


    @Transactional
    @PreAuthorize("hasAuthority('MANAGER')")
    public UserReadDto create(UserCreateEditDto userDto) {
        return Optional.of(userDto)
                .map(dto -> userCreateEditMapper.map(dto))
                .map(userRepository::save)
                .map(userReadMapper::map)
                .orElseThrow();
    }



    @Transactional
    @PreAuthorize("hasAuthority('MANAGER')")
    public Optional<UserReadDto> update(Long id, UserCreateEditDto userDto) {
        return userRepository.findById(id)
                .map(entity -> userCreateEditMapper.map(userDto, entity))
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map);
    }

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean delete(Long id) {
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Faild to retrieve user: " + username));
    }
}
