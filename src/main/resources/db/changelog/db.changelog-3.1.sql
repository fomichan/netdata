--liquibase formatted sql

--changeset fomich:1

ALTER TABLE multiplexer
ALTER COLUMN serial_number TYPE varchar(32);

--changeset fomich:2
ALTER TABLE module
    ALTER COLUMN serial_number TYPE varchar(32);



