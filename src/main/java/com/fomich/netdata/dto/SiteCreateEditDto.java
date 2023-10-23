package com.fomich.netdata.dto;

public record SiteCreateEditDto(
                                String name,
                                String region,
                                String city,
                                String address
                          ) {
}
