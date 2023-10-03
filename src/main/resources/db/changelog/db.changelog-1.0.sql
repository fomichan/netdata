--liquibase formatted sql

--changeset fomich:1
create table channel (
                         id serial not null,
                         name varchar(255) not null unique,
                         primary key (id)
);
--rollback DROP TABLE channel;

--changeset fomich:2
create table module (
                        id serial not null,
                        multiplexer_id integer,
                        serial_number integer,
                        module_type varchar(255) check (module_type in ('SYN4E','LOMIF','NEBRO','SYNAC','SUBH','EXLAN')),
                        primary key (id)
);

--changeset fomich:3
create table multiplexer (
                             id serial not null,
                             serial_number integer,
                             site_id integer,
                             name varchar(255) not null unique,
                             primary key (id)
);

--changeset fomich:4
create table multiplexer_channel (
                                     channel_id integer,
                                     id serial not null,
                                     multiplexer_id integer,
                                     primary key (id)
);

--changeset fomich:5
create table site (
                      id serial not null,
                      name varchar(255) not null unique,
                      primary key (id)
);


