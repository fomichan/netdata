package com.fomich.netdata.service;

import com.fomich.netdata.database.entity.QMultiplexer;
import com.fomich.netdata.database.querydsl.QPredicates;
import com.fomich.netdata.database.repository.MultiplexerRepository;
import com.fomich.netdata.dto.MultiplexerCreateEditDto;
import com.fomich.netdata.dto.MultiplexerFilter;
import com.fomich.netdata.dto.MultiplexerReadDto;
import com.fomich.netdata.dto.MultiplexerShowDetailsDto;
import com.fomich.netdata.mapper.MultiplexerCreateEditMapper;
import com.fomich.netdata.mapper.MultiplexerReadMapper;
import com.fomich.netdata.mapper.MultiplexerShowDetailsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MultiplexerService {

    private final MultiplexerRepository multiplexerRepository;
    private final MultiplexerReadMapper multiplexerReadMapper;
    private final MultiplexerCreateEditMapper multiplexerCreateEditMapper;
    private final MultiplexerShowDetailsMapper multiplexerShowDetailsMapper;




    //@PostFilter("") // можно отфильтровать возвращаемые данные для разных пользователей по условию см 107
    public Optional<MultiplexerShowDetailsDto> findById(Integer id) {
        return multiplexerRepository.findById(id)
                .map(multiplexerShowDetailsMapper::map);
    }



    public Page<MultiplexerReadDto> findAll(MultiplexerFilter filter, Pageable pageable) {
        var predicate = QPredicates.builder()
                .add(filter.name(), QMultiplexer.multiplexer.name::containsIgnoreCase)
                .add(filter.serialNumber(), QMultiplexer.multiplexer.serialNumber::containsIgnoreCase)
                .add(filter.siteId(), QMultiplexer.multiplexer.site.id::eq)
                .build();


        return multiplexerRepository.findAll(predicate, pageable)
                .map(multiplexerReadMapper::map);
    }





//    public List<MultiplexerReadDto> findAll(MultiplexerFilter filter) {
//
//        return multiplexerRepository.findAllByFilter(filter).stream()
//                .map(multiplexerReadMapper::map)
//                .toList();
//    }


//    public List<MultiplexerReadDto> findAll() {
//        return multiplexerRepository.findAll().stream()
//                .map(multiplexerReadMapper::map)
//                .toList();
//    }




//    public Optional<MultiplexerReadDto> findById(Integer id) {
//        return multiplexerRepository.findById(id)
//                .map(multiplexerReadMapper::map);
//    }


    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public MultiplexerReadDto create(MultiplexerCreateEditDto muxDto) {
        return Optional.of(muxDto)
                .map(multiplexerCreateEditMapper::map)
                .map(multiplexerRepository::save)
                .map(multiplexerReadMapper::map)
                .orElseThrow();
    }


    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public Optional<MultiplexerReadDto> update(Integer id, MultiplexerCreateEditDto muxDto) {
        return multiplexerRepository.findById(id)
                .map(entity -> multiplexerCreateEditMapper.map(muxDto, entity))
                .map(multiplexerRepository::saveAndFlush)
                .map(multiplexerReadMapper::map);
    }


    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean delete(Integer id) {
        return multiplexerRepository.findById(id)
                .map(entity -> {
                    multiplexerRepository.delete(entity);
                    multiplexerRepository.flush();
                    return true;
                })
                .orElse(false);
    }

}
