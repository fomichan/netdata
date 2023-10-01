package com.fomich.netdata.service;

import com.fomich.netdata.database.repository.MultiplexerRepository;
import com.fomich.netdata.dto.MultiplexerIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MultiplexerService {

    private final MultiplexerRepository multiplexerRepository;

    public Optional<MultiplexerIdDto> findById(Integer id) {
        return multiplexerRepository.findById(id)
                .map(entity -> new MultiplexerIdDto(entity.getId()));
    }
}
