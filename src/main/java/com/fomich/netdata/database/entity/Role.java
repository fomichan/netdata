package com.fomich.netdata.database.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    ADMIN,
    MANAGER;

    @Override
    public String getAuthority() {
        return name();
    }
}
