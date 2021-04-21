ALTER SEQUENCE global_seq RESTART WITH 10000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO restaurant(name, id)
values ('tea_home', 10010),
       ('home_sweat_home', 10020);

INSERT INTO dishes (name, price, restaurant_id)
VALUES ('borsch', 40, 10010),
       ('cutlet_kiev', 120, 10020),
       ('compote', 20, 10020),
       ('pasta', 100, 10010),
       ('muffin', 55, 10010);

-- INSERT INTO restaurant (name, menu)
--     VALUE ('tea_House', 10004)
