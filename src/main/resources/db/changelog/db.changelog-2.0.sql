--liquibase formatted sql

--changeset fomich:1
ALTER TABLE multiplexer

    ADD COLUMN created_at TIMESTAMP;

ALTER TABLE multiplexer
    ADD COLUMN modified_at TIMESTAMP;

ALTER TABLE multiplexer
    ADD COLUMN created_by VARCHAR(32);

ALTER TABLE multiplexer
    ADD COLUMN modified_by VARCHAR(32);
