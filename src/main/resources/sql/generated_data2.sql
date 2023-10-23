-- Создание 150 записей в таблице site
INSERT INTO site (name, region, city, address)
SELECT
        'Site ' || generate_series(1, 150),
        'Region ' || floor(random() * 10) + 1,
        'City ' || floor(random() * 50) + 1,
        'Address ' || floor(random() * 50) + 1
;

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
-- INSERT INTO module (multiplexer_id, serial_number, module_type, slot)
-- SELECT
--     m.id,
--     md5(random()::text),
--     CASE floor(random() * 6)
--         WHEN 0 THEN 'SYN4E'
--         WHEN 1 THEN 'LOMIF'
--         WHEN 2 THEN 'NEBRO'
--         WHEN 3 THEN 'SYNAC'
--         WHEN 4 THEN 'SUBH'
--         ELSE 'EXLAN'
--         END,
--     floor(random() * 20 + 1)::integer
-- FROM
--     multiplexer m
--         JOIN
--     (SELECT DISTINCT multiplexer_id FROM multiplexer_channel) mc
--     ON
--             m.id = mc.multiplexer_id;


-- Создаем модули для каждого мультиплексора
INSERT INTO module (multiplexer_id, serial_number, slot, module_type)
SELECT
    m.id AS multiplexer_id,
    md5(random()::text) AS serial_number,
    (FLOOR(RANDOM() * 20) + 1) AS slot,
    CASE FLOOR(RANDOM() * 10)
        WHEN 0 THEN 'SYN4E'
        WHEN 1 THEN 'LOMIF'
        WHEN 2 THEN 'NEBRO'
        WHEN 3 THEN 'SYNAC'
        WHEN 4 THEN 'SUBH'
        WHEN 5 THEN 'EXLAN'
        WHEN 6 THEN 'NEMSG'
        WHEN 7 THEN 'SYNAM'
        WHEN 8 THEN 'POSUM'
        ELSE 'COBUX'
        END AS module_type
FROM multiplexer m
         CROSS JOIN generate_series(1, 6); -- Здесь указываем 6, чтобы создать ровно 6 записей для каждого мультиплексора


