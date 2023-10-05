package com.fomich.netdata.service;

import com.fomich.netdata.database.entity.Multiplexer;
import com.fomich.netdata.database.repository.MultiplexerRepository;
import com.fomich.netdata.dto.MultiplexerCreateEditDto;
import com.fomich.netdata.dto.MultiplexerIdDto;
import com.fomich.netdata.dto.MultiplexerReadDto;
import com.fomich.netdata.mapper.MultiplexerCreateEditMapper;
import com.fomich.netdata.mapper.MultiplexerReadMapper;
import lombok.RequiredArgsConstructor;
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


//    public Optional<MultiplexerIdDto> findById(Integer id) {
//        return multiplexerRepository.findById(id)
//                .map(entity -> new MultiplexerIdDto(entity.getId()));
//    }


    public List<MultiplexerReadDto> findAll() {
        return multiplexerRepository.findAll().stream()
                .map(multiplexerReadMapper::map)
                .toList();
    }


    public Optional<MultiplexerReadDto> findById(Integer id) {
        return multiplexerRepository.findById(id)
                .map(multiplexerReadMapper::map);
    }


    @Transactional
    public MultiplexerReadDto create(MultiplexerCreateEditDto muxDto) {
        return Optional.of(muxDto)
                .map(multiplexerCreateEditMapper::map)
                .map(multiplexerRepository::save)
                .map(multiplexerReadMapper::map)
                .orElseThrow();
    }


    @Transactional
    public Optional<MultiplexerReadDto> update(Integer id, MultiplexerCreateEditDto muxDto) {
        return multiplexerRepository.findById(id)
                .map(entity -> multiplexerCreateEditMapper.map(muxDto, entity))
                .map(multiplexerRepository::saveAndFlush)
                .map(multiplexerReadMapper::map);
    }


    @Transactional
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
