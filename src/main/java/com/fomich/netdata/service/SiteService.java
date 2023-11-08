package com.fomich.netdata.service;

import com.fomich.netdata.database.entity.QSite;
import com.fomich.netdata.database.querydsl.QPredicates;
import com.fomich.netdata.database.repository.SiteRepository;
import com.fomich.netdata.dto.SiteCreateEditDto;
import com.fomich.netdata.dto.SiteFilter;
import com.fomich.netdata.dto.SiteReadDto;
import com.fomich.netdata.dto.SiteShortReadDto;
import com.fomich.netdata.mapper.SiteCreateEditMapper;
import com.fomich.netdata.mapper.SiteReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SiteService {

    private final SiteRepository siteRepository;
    private final SiteReadMapper siteReadMapper;
    private final SiteCreateEditMapper siteCreateEditMapper;



    public List<SiteReadDto> findAll() {
        return siteRepository.findAll().stream()
                .map(siteReadMapper::map)
                .toList();
    }

    // Для не детализированного списка sites
    public List<SiteShortReadDto> findAllShort() {
        return siteRepository.findAllBy();
    }




    public Page<SiteReadDto> findAll(SiteFilter filter, Pageable pageable) {
        var predicate = QPredicates.builder()
                // coalesce("") будет заменять null на пустую строку, и затем будет применено условие containsIgnoreCase к результату. Это позволит корректно фильтровать сущности, даже если поле name в базе данных содержит null.
                // BooleanExpression predicate = QSite.site.name
                //    .coalesce("") // Заменить null на пустую строку
                //    .containsIgnoreCase(filter.name());
                .add(filter.name(), QSite.site.name.coalesce("")::containsIgnoreCase) //
                .add(filter.region(), QSite.site.region.coalesce("")::containsIgnoreCase)
                .add(filter.city(), QSite.site.city.coalesce("")::containsIgnoreCase)
                .add(filter.address(), QSite.site.address.coalesce("")::containsIgnoreCase)
                .build();

        return siteRepository.findAll(predicate, pageable)
                .map(siteReadMapper::map);
    }




    public Optional<SiteReadDto> findById(Integer id) {
        return siteRepository.findById(id)
                .map(siteReadMapper::map);
    }


    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public SiteReadDto create(SiteCreateEditDto site) {
        return Optional.of(site)
                .map(siteCreateEditMapper::map)
                .map(siteRepository::save)
                .map(siteReadMapper::map)
                .orElseThrow();
    }


    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public Optional<SiteReadDto> update(Integer id, SiteCreateEditDto site) {
        return siteRepository.findById(id)
                .map(entity -> siteCreateEditMapper.map(site, entity))
                .map(siteRepository::saveAndFlush)
                .map(siteReadMapper::map);
    }


    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean delete(Integer id) {
        return siteRepository.findById(id)
                .map(entity -> {
                    siteRepository.delete(entity);
                    siteRepository.flush();
                    return true;
                })
                .orElse(false);
    }



}
