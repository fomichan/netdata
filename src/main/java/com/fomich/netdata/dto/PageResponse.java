package com.fomich.netdata.dto;

import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

@Value
public class PageResponse<T> {
    List<T> content;
    Metadata metadata;

    public static <T> PageResponse<T> of(Page<T> page) {
        var metadata = new Metadata(page.getNumber() + 1,
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                extractSortField(page.getSort()), // Извлекаем поле сортировки
                extractSortDirection(page.getSort()) // Извлекаем направление сортировки
        );
        return new PageResponse<>(page.getContent(), metadata);
    }

    @Value
    public static class Metadata {
        int page;
        int size;
        long totalElements;
        long totalPages;
        String sortField; // Поле для хранения информации о поле сортировки
        Sort.Direction sortDirection; // Поле для хранения информации о направлении сортировки
    }

    private static String extractSortField(Sort sort) {
        return sort.stream()
                .map(order -> order.getProperty())
                .findFirst()
                .orElse(null);
    }

    private static Sort.Direction extractSortDirection(Sort sort) {
        return sort.get().findFirst()
                .map(order -> order.getDirection())
                .orElse(Sort.Direction.ASC);
    }
}
