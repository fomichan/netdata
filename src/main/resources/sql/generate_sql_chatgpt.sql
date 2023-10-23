

create table channel
(
    id   serial
        primary key,
    name varchar(255) not null
        unique
);


create table module
(
    id             serial
        primary key,
    multiplexer_id integer,
    serial_number  varchar(32),
    slot           integer,
    module_type    varchar(255)
);


create table multiplexer
(
    id            serial
        primary key,
    serial_number varchar(32),
    site_id       integer,
    name          varchar(255) not null
        unique,

);


create table multiplexer_channel
(
    channel_id     integer,
    id             serial
        primary key,
    multiplexer_id integer,
    unique (channel_id, multiplexer_id)
);


create table site
(
    id      serial
        primary key,
    name    varchar(255) not null
        unique,
    region  varchar(255),
    city    varchar(255),
    address varchar(255)
);


/*

Для данных таблиц сгенерировать данные:
Количество multiplexer 210.
В каждом multiplexer установлено по 8 различных module в уникальных слотах.
module.module_type могут быть только такими: SYN4E, LOMIF, NEBRO, SYNAC, SUBH, EXLAN, NEMSG, SYNAM, POSUM, COBUX.
Site это объект куда устанавливается мультиплексор. Количество Site 70. На каждом site по 3 multiplexer.
multiplexer и channel связаны через multiplexer_channel.
multiplexer_channel.channel_id ссылается на channel.id
multiplexer_channel.multiplexer_id ссылается на multiplexer.id
В каждом channel по 15 различных multiplexer
В каждом multiplexer по 20 различных channel

 */


