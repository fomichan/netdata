package com.fomich.netdata.dto;

import java.time.LocalDate;

public record UserFilter(String username,
                         String firstname,
                         String lastname) {
}
