DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 10000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');



INSERT INTO dishes (name, price)
VALUES ('borsch', 40),
       ('cutlet_kiev', 120),
       ('compote', 20),
       ('pasta', 100),
       ('muffin', 55);

--
-- INSERT INTO restaurant (name, menu)
--     VALUE ('tea_House', dishes_id) {
--
-- }


-- INSERT INTO meals (date_time, description, calories, user_id)
-- VALUES ('2020-01-30 10:00:00', 'Завтрак', 500, 100000),
--        ('2020-01-30 13:00:00', 'Обед', 1000, 100000),
--        ('2020-01-30 20:00:00', 'Ужин', 500, 100000),
--        ('2020-01-31 0:00:00', 'Еда на граничное значение', 100, 100000),
--        ('2020-01-31 10:00:00', 'Завтрак', 500, 100000),
--        ('2020-01-31 13:00:00', 'Обед', 1000, 100000),
--        ('2020-01-31 20:00:00', 'Ужин', 510, 100000),
--        ('2020-01-31 14:00:00', 'Админ ланч', 510, 100001),
--        ('2020-01-31 21:00:00', 'Админ ужин', 1500, 100001);