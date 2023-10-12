-- Создание 150 записей в таблице site
INSERT INTO site (name)
SELECT
        'Site ' || generate_series(1, 150);

-- Создание 400 записей в таблице multiplexer
INSERT INTO multiplexer (serial_number, site_id, name)
SELECT
    md5(random()::text),
    floor(random() * 150) + 1,
    'Multiplexer ' || generate_series(1, 400);

-- Создание 3000 записей в таблице channel
INSERT INTO channel (name)
SELECT
        'Channel ' || generate_series(1, 3000);

-- Создание связей между multiplexer и channel через таблицу multiplexer_channel
INSERT INTO multiplexer_channel (channel_id, multiplexer_id)
SELECT
    generate_series(1, 3000),
    floor(random() * 400) + 1;

-- Создание 4-5 modules для каждого multiplexer
INSERT INTO module (multiplexer_id, serial_number, module_type)
SELECT
    m.id,
    md5(random()::text),
    CASE floor(random() * 6)
        WHEN 0 THEN 'SYN4E'
        WHEN 1 THEN 'LOMIF'
        WHEN 2 THEN 'NEBRO'
        WHEN 3 THEN 'SYNAC'
        WHEN 4 THEN 'SUBH'
        ELSE 'EXLAN'
        END
FROM
    multiplexer m
        JOIN
    (SELECT DISTINCT multiplexer_id FROM multiplexer_channel) mc
    ON
            m.id = mc.multiplexer_id;
