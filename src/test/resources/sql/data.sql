

INSERT INTO site (id, name)
VALUES (1, 'site1'),
       (2, 'site2'),
       (3, 'site3'),
       (4, 'site4'),
       (5, 'site5');
SELECT SETVAL('site_id_seq', (SELECT MAX(id) FROM site));



INSERT INTO multiplexer (id, name, serial_number, site_id)
VALUES (1, 's1mux1', 196543343, (SELECT id FROM site WHERE name = 'site1')),
       (2, 's1mux2', 345353123, (SELECT id FROM site WHERE name = 'site1')),
       (3, 's2mux1', 123154533, (SELECT id FROM site WHERE name = 'site2')),
       (4, 's2mux2', 978673123, (SELECT id FROM site WHERE name = 'site2')),
       (5, 's3mux1', 153245345, (SELECT id FROM site WHERE name = 'site3')),
       (6, 's3mux2', 345345424, (SELECT id FROM site WHERE name = 'site3')),
       (7, 's4mux1', 789752253, (SELECT id FROM site WHERE name = 'site4')),
       (8, 's5mux1', 136975752, (SELECT id FROM site WHERE name = 'site5')),
       (9, 's5mux2', 146575234, (SELECT id FROM site WHERE name = 'site5')),
       (10, 's5mux3', 123453455, (SELECT id FROM site WHERE name = 'site5'));
SELECT SETVAL('multiplexer_id_seq', (SELECT MAX(id) FROM multiplexer));

INSERT INTO module (id, module_type, serial_number, multiplexer_id)
VALUES (1, 'SYN4E', 456345634, (SELECT id FROM multiplexer WHERE name = 's1mux1')),
       (2, 'LOMIF', 345634745, (SELECT id FROM multiplexer WHERE name = 's1mux1')),
       (3, 'SYN4E', 898796578, (SELECT id FROM multiplexer WHERE name = 's1mux2')),
       (4, 'LOMIF', 456848995, (SELECT id FROM multiplexer WHERE name = 's1mux2')),
       (5, 'SYN4E', 568478678, (SELECT id FROM multiplexer WHERE name = 's2mux1')),
       (6, 'LOMIF', 634563456, (SELECT id FROM multiplexer WHERE name = 's2mux1')),
       (7, 'SYN4E', 345634563, (SELECT id FROM multiplexer WHERE name = 's2mux2')),
       (8, 'LOMIF', 634563456, (SELECT id FROM multiplexer WHERE name = 's2mux2')),
       (9, 'LOMIF', 589679678, (SELECT id FROM multiplexer WHERE name = 's3mux1')),
       (10, 'SYN4E', 567456745, (SELECT id FROM multiplexer WHERE name = 's3mux1')),
       (11, 'LOMIF', 967896787, (SELECT id FROM multiplexer WHERE name = 's3mux2')),
       (12, 'SYN4E', 456745745, (SELECT id FROM multiplexer WHERE name = 's3mux2')),
       (13, 'LOMIF', 547656777, (SELECT id FROM multiplexer WHERE name = 's4mux1')),
       (14, 'SYN4E', 456745686, (SELECT id FROM multiplexer WHERE name = 's4mux1')),
       (15, 'LOMIF', 345634563, (SELECT id FROM multiplexer WHERE name = 's5mux1')),
       (16, 'SYN4E', 345634544, (SELECT id FROM multiplexer WHERE name = 's5mux1')),
       (17, 'LOMIF', 245634565, (SELECT id FROM multiplexer WHERE name = 's5mux2')),
       (18, 'SYN4E', 634563456, (SELECT id FROM multiplexer WHERE name = 's5mux2')),
       (19, 'LOMIF', 345634563, (SELECT id FROM multiplexer WHERE name = 's5mux3')),
       (20, 'SYN4E', 985797898, (SELECT id FROM multiplexer WHERE name = 's5mux3'));
SELECT SETVAL('module_id_seq', (SELECT MAX(id) FROM module));

INSERT INTO channel (id, name)
VALUES (1, 'channal01'),
       (2, 'channal02'),
       (3, 'channal03'),
       (4, 'channal04'),
       (5, 'channal05'),
       (6, 'channal06');
SELECT SETVAL('channel_id_seq', (SELECT MAX(id) FROM channel));


INSERT INTO multiplexer_channel(id, multiplexer_id, channel_id)
VALUES (1, (SELECT id FROM multiplexer WHERE name = 's1mux2'), (SELECT id FROM channel WHERE name = 'channal01')),
       (2, (SELECT id FROM multiplexer WHERE name = 's3mux1'), (SELECT id FROM channel WHERE name = 'channal01')),
       (3, (SELECT id FROM multiplexer WHERE name = 's5mux2'), (SELECT id FROM channel WHERE name = 'channal01')),
       (4, (SELECT id FROM multiplexer WHERE name = 's3mux1'), (SELECT id FROM channel WHERE name = 'channal02')),
       (5, (SELECT id FROM multiplexer WHERE name = 's4mux1'), (SELECT id FROM channel WHERE name = 'channal02')),
       (6, (SELECT id FROM multiplexer WHERE name = 's2mux2'), (SELECT id FROM channel WHERE name = 'channal02')),
       (7, (SELECT id FROM multiplexer WHERE name = 's5mux3'), (SELECT id FROM channel WHERE name = 'channal03')),
       (8, (SELECT id FROM multiplexer WHERE name = 's1mux1'), (SELECT id FROM channel WHERE name = 'channal03')),
       (9, (SELECT id FROM multiplexer WHERE name = 's2mux1'), (SELECT id FROM channel WHERE name = 'channal03')),
       (10, (SELECT id FROM multiplexer WHERE name = 's5mux1'), (SELECT id FROM channel WHERE name = 'channal04')),
       (11, (SELECT id FROM multiplexer WHERE name = 's4mux1'), (SELECT id FROM channel WHERE name = 'channal04')),
       (12, (SELECT id FROM multiplexer WHERE name = 's3mux2'), (SELECT id FROM channel WHERE name = 'channal04')),
       (13, (SELECT id FROM multiplexer WHERE name = 's5mux2'), (SELECT id FROM channel WHERE name = 'channal05')),
       (14, (SELECT id FROM multiplexer WHERE name = 's3mux2'), (SELECT id FROM channel WHERE name = 'channal05')),
       (15, (SELECT id FROM multiplexer WHERE name = 's1mux1'), (SELECT id FROM channel WHERE name = 'channal05')),
       (16, (SELECT id FROM multiplexer WHERE name = 's2mux2'), (SELECT id FROM channel WHERE name = 'channal06')),
       (17, (SELECT id FROM multiplexer WHERE name = 's3mux1'), (SELECT id FROM channel WHERE name = 'channal06')),
       (18, (SELECT id FROM multiplexer WHERE name = 's5mux1'), (SELECT id FROM channel WHERE name = 'channal06'));
SELECT SETVAL('multiplexer_channel_id_seq', (SELECT MAX(id) FROM multiplexer_channel));






