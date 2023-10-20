
create table channel (
                         id serial not null,
                         name varchar(255) not null unique,
                         primary key (id)
)

create table module (
                        id serial not null,
                        multiplexer_id integer,
                        serial_number varchar(32),
                        module_type varchar(255) check (module_type in ('SYN4E','LOMIF','NEBRO','SYNAC','SUBH','EXLAN')),
                        primary key (id)
)

create table multiplexer (
                             id serial not null,
                             serial_number varchar(32),
                             site_id integer,
                             name varchar(255) not null unique,
                             primary key (id)
)

create table multiplexer_channel (
                                     channel_id integer,
                                     id serial not null,
                                     multiplexer_id integer,
                                     primary key (id)
)

create table site (
                      id serial not null,
                      name varchar(255) not null unique,
                      primary key (id)
)

alter table if exists module
    add constraint FKb0e4qn1m657j8m4iepvt5t2f9
        foreign key (multiplexer_id)
            references multiplexer

alter table if exists multiplexer
    add constraint FKics4ujtw5qp74ygxxldjh44rr
        foreign key (site_id)
            references site

alter table if exists multiplexer_channel
    add constraint FKdnn7ipjfsqen6e9ki9fsw3q0
        foreign key (channel_id)
            references channel

alter table if exists multiplexer_channel
    add constraint FK3uje0oi3hppbgrwq517fk445e
        foreign key (multiplexer_id)
            references multiplexer




select
    c1_0.id,
    c1_0.name
from
    channel c1_0
where
    exists(select
               1
           from
               multiplexer_channel m1_0
                   join
               multiplexer m2_0
               on m2_0.id=m1_0.multiplexer_id
           where
               m2_0.site_id=2
             and c1_0.id=m1_0.channel_id);



select
    c.id,
    c.name
from
    channel c
where
    exists(select
                   1
               from
                   multiplexer_channel mc
               where
                   mc.multiplexer_id=3
                 and c.id=mc.channel_id);




SELECT c.*
FROM channel c
         INNER JOIN multiplexer_channel mc ON c.id = mc.channel_id
WHERE mc.multiplexer_id = 3;

