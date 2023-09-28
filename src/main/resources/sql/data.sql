INSERT INTO site (name)
VALUES ('site1'),
       ('site2'),
       ('site3'),
       ('site4'),
       ('site5');




INSERT INTO module (module_type, serial_number, multiplexer_id)
VALUES ('SYN4E', 456345634, (SELECT id FROM multiplexer WHERE name = 's1mux1')),
       ('LOMIF', 345634745, (SELECT id FROM multiplexer WHERE name = 's1mux1')),
       ('SYN4E', 898796578, (SELECT id FROM multiplexer WHERE name = 's1mux2')),
       ('LOMIF', 456848995, (SELECT id FROM multiplexer WHERE name = 's1mux2')),
       ('SYN4E', 568478678, (SELECT id FROM multiplexer WHERE name = 's2mux1')),
       ('LOMIF', 634563456, (SELECT id FROM multiplexer WHERE name = 's2mux1')),
       ('SYN4E', 345634563, (SELECT id FROM multiplexer WHERE name = 's2mux2')),
       ('LOMIF', 634563456, (SELECT id FROM multiplexer WHERE name = 's2mux2')),
       ('LOMIF', 589679678, (SELECT id FROM multiplexer WHERE name = 's3mux1')),
       ('SYN4E', 567456745, (SELECT id FROM multiplexer WHERE name = 's3mux1')),
       ('LOMIF', 967896787, (SELECT id FROM multiplexer WHERE name = 's3mux2')),
       ('SYN4E', 456745745, (SELECT id FROM multiplexer WHERE name = 's3mux2')),
       ('LOMIF', 547656777, (SELECT id FROM multiplexer WHERE name = 's4mux1')),
       ('SYN4E', 456745686, (SELECT id FROM multiplexer WHERE name = 's4mux1')),
       ('LOMIF', 345634563, (SELECT id FROM multiplexer WHERE name = 's5mux1')),
       ('SYN4E', 345634544, (SELECT id FROM multiplexer WHERE name = 's5mux1')),
       ('LOMIF', 245634565, (SELECT id FROM multiplexer WHERE name = 's5mux2')),
       ('SYN4E', 634563456, (SELECT id FROM multiplexer WHERE name = 's5mux2')),
       ('LOMIF', 345634563, (SELECT id FROM multiplexer WHERE name = 's5mux3')),
       ('SYN4E', 985797898, (SELECT id FROM multiplexer WHERE name = 's5mux3'));





INSERT INTO multiplexer (name, serial_number, site_id)
VALUES ('s1mux1', 196543343, (SELECT id FROM site WHERE name = 'site1')),
       ('s1mux2', 345353123, (SELECT id FROM site WHERE name = 'site1')),
       ('s2mux1', 123154533, (SELECT id FROM site WHERE name = 'site1')),
       ('s2mux2', 978673123, (SELECT id FROM site WHERE name = 'site1')),
       ('s3mux1', 153245345, (SELECT id FROM site WHERE name = 'site1')),
       ('s3mux2', 345345424, (SELECT id FROM site WHERE name = 'site1')),
       ('s4mux1', 789752253, (SELECT id FROM site WHERE name = 'site1')),
       ('s5mux1', 136975752, (SELECT id FROM site WHERE name = 'site1')),
       ('s5mux2', 146575234, (SELECT id FROM site WHERE name = 'site1')),
       ('s5mux3', 123453455, (SELECT id FROM site WHERE name = 'site1'));



INSERT INTO channel (name)
VALUES ('channal01'),
       ('channal02'),
       ('channal03'),
       ('channal04'),
       ('channal05'),
       ('channal06');


INSERT INTO multiplexer_channel(multiplexer_id, channel_id)
VALUES ((SELECT id FROM multiplexer WHERE name = 's1mux2'), (SELECT id FROM channel WHERE name = 'channal01')),
       ((SELECT id FROM multiplexer WHERE name = 's3mux1'), (SELECT id FROM channel WHERE name = 'channal01')),
       ((SELECT id FROM multiplexer WHERE name = 's5mux2'), (SELECT id FROM channel WHERE name = 'channal01')),
       ((SELECT id FROM multiplexer WHERE name = 's3mux1'), (SELECT id FROM channel WHERE name = 'channal02')),
       ((SELECT id FROM multiplexer WHERE name = 's4mux1'), (SELECT id FROM channel WHERE name = 'channal02')),
       ((SELECT id FROM multiplexer WHERE name = 's2mux2'), (SELECT id FROM channel WHERE name = 'channal02')),
       ((SELECT id FROM multiplexer WHERE name = 's5mux3'), (SELECT id FROM channel WHERE name = 'channal03')),
       ((SELECT id FROM multiplexer WHERE name = 's1mux1'), (SELECT id FROM channel WHERE name = 'channal03')),
       ((SELECT id FROM multiplexer WHERE name = 's2mux1'), (SELECT id FROM channel WHERE name = 'channal03')),
       ((SELECT id FROM multiplexer WHERE name = 's5mux1'), (SELECT id FROM channel WHERE name = 'channal04')),
       ((SELECT id FROM multiplexer WHERE name = 's4mux1'), (SELECT id FROM channel WHERE name = 'channal04')),
       ((SELECT id FROM multiplexer WHERE name = 's3mux2'), (SELECT id FROM channel WHERE name = 'channal04')),
       ((SELECT id FROM multiplexer WHERE name = 's5mux2'), (SELECT id FROM channel WHERE name = 'channal05')),
       ((SELECT id FROM multiplexer WHERE name = 's3mux2'), (SELECT id FROM channel WHERE name = 'channal05')),
       ((SELECT id FROM multiplexer WHERE name = 's1mux1'), (SELECT id FROM channel WHERE name = 'channal05')),
       ((SELECT id FROM multiplexer WHERE name = 's2mux2'), (SELECT id FROM channel WHERE name = 'channal06')),
       ((SELECT id FROM multiplexer WHERE name = 's3mux1'), (SELECT id FROM channel WHERE name = 'channal06')),
       ((SELECT id FROM multiplexer WHERE name = 's5mux1'), (SELECT id FROM channel WHERE name = 'channal06'));

