--liquibase formatted sql

--changeset fomich:1
create table revision
(
    id serial primary key,
    timestamp bigint
);


--changeset fomich:2
create table multiplexer_aud
(
    id            integer not null,
    rev           integer not null,
    revtype       smallint,
    name          varchar(255),
    serial_number varchar(32),
    site_id       integer,
    primary key (rev, id)
);


