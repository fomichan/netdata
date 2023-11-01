package com.fomich.netdata.service;

import com.fomich.netdata.database.repository.ModuleRepository;
import com.fomich.netdata.dto.ModuleCreateEditDto;
import com.fomich.netdata.dto.ModuleReadDto;
import com.fomich.netdata.mapper.ModuleCreateEditMapper;
import com.fomich.netdata.mapper.ModuleReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final ModuleReadMapper moduleReadMapper;
    private final ModuleCreateEditMapper moduleCreateEditMapper;





    public Optional<ModuleReadDto> findById(Integer id) {
        return moduleRepository.findById(id)
                .map(moduleReadMapper::map);
    }


    @Transactional
    public ModuleReadDto create(ModuleCreateEditDto moduleCreateEditDto) {
        return Optional.of(moduleCreateEditDto)
                .map(moduleCreateEditMapper::map)
                .map(moduleRepository::save)
                .map(moduleReadMapper::map)
                .orElseThrow();
    }


    @Transactional
    public Optional<ModuleReadDto> update(Integer id, ModuleCreateEditDto moduleCreateEditDto) {
        return moduleRepository.findById(id)
                .map(entity -> moduleCreateEditMapper.map(moduleCreateEditDto, entity))
                .map(moduleRepository::saveAndFlush)
                .map(moduleReadMapper::map);
    }


    @Transactional
    public boolean delete(Integer id) {
        return moduleRepository.findById(id)
                .map(entity -> {
                    moduleRepository.delete(entity);
                    moduleRepository.flush();
                    return true;
                })
                .orElse(false);
    }

}
