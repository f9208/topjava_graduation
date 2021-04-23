ALTER SEQUENCE global_seq RESTART WITH 10000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO restaurant(name)
values ('tea_home'),
       ('home_sweat_home');

INSERT INTO dishes (name, price, restaurant_id, added, enabled)
VALUES ('borscht', 40, 10002, '2020-04-10 10:00:00', true),
       ('cutlet_kiev', 120, 10003, '2020-04-10 10:00:00', true),
       ('compote', 20, 10003, '2020-04-10 10:00:00', true),
       ('pasta', 100, 10003, '2020-04-10 10:00:00', true),
       ('soup', 120, 10003, '2020-04-10 10:00:00', false),
       ('fish', 160, 10003, '2020-04-10 10:00:00', true),
       ('pancake', 30, 10003, '2020-04-10 10:00:00', true),
       ('muffin', 55, 10002, '2020-04-10 10:00:00', false);

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 10000),
       ('ADMIN', 10001),
       ('USER', 10001);

INSERT INTO vote (restaurant_id, user_id)
VALUES (10002, 10000),
       (10003, 10001);