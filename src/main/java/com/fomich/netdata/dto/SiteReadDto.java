package com.fomich.netdata.dto;

public record SiteReadDto(Integer id,
                          String name,
                          String region,
                          String city,
                          String address
                          ) {
}
