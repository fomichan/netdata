-- Вставка данных в таблицу site
INSERT INTO site (name)
SELECT 'Site ' || generate_series(1, 50);

-- Вставка данных в таблицу multiplexer
INSERT INTO multiplexer (serial_number, site_id, name)
SELECT
    floor(random() * 10000), -- случайный серийный номер
    floor(random() * 50) + 1, -- случайный site_id от 1 до 50
    'Multiplexer ' || generate_series(1, 150); -- имя мультиплексора

-- Вставка данных в таблицу module
INSERT INTO module (multiplexer_id, serial_number, module_type)
SELECT
    id, -- id мультиплексора
    floor(random() * 10000), -- случайный серийный номер
    (array['SYN4E', 'LOMIF', 'NEBRO', 'SYNAC', 'SUBH', 'EXLAN'])[floor(random() * 6) + 1] -- случайный module_type
FROM multiplexer
WHERE random() < 0.75; -- примерно 75% мультиплексоров имеют модули

-- Вставка данных в таблицу channel
INSERT INTO channel (name)
SELECT 'Channel ' || generate_series(1, 300);


-- Вставка данных в таблицу multiplexer_channel
INSERT INTO multiplexer_channel (channel_id, multiplexer_id)
SELECT
    c.id,
    m.id
FROM channel c
         CROSS JOIN (
    SELECT id
    FROM multiplexer
    ORDER BY random()
    LIMIT 150 -- Выбираем все multiplexer
) m
GROUP BY m.id, c.id
HAVING COUNT(*) < 24 -- Ограничить количество связей 24 для каждого multiplexer
ORDER BY random();
