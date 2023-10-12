--liquibase formatted sql

--changeset fomich:1

ALTER TABLE multiplexer_aud
ALTER COLUMN serial_number TYPE varchar(32);





