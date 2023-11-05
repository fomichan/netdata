--liquibase formatted sql


--changeset fomich:1
CREATE TABLE IF NOT EXISTS users
(
    id BIGSERIAL not null ,
    username VARCHAR(64) NOT NULL UNIQUE ,
    password VARCHAR(128) DEFAULT '{noop}123',
    birth_date DATE,
    firstname VARCHAR(64),
    lastname VARCHAR(64),
    role VARCHAR(32),

    created_at TIMESTAMP,
    modified_at TIMESTAMP,
    created_by VARCHAR(32),
    modified_by VARCHAR(32)
);


--changeset fomich:2
CREATE TABLE IF NOT EXISTS users_aud
(
    id BIGINT,
    rev INT REFERENCES revision (id),
    revtype SMALLINT ,
    username VARCHAR(64) UNIQUE ,
    password VARCHAR(128),
    birth_date DATE,
    firstname VARCHAR(64),
    lastname VARCHAR(64),
    role VARCHAR(32)

);


