ALTER SEQUENCE USER_SEQ RESTART WITH 10000;
ALTER SEQUENCE DISH_SEQ RESTART WITH 10000;
ALTER SEQUENCE RESTAURANT_SEQ RESTART WITH 10000;
ALTER SEQUENCE VOTE_SEQ RESTART WITH 10000;

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', '{noop}123456789'),
       ('Jonny', 'jonny@gmail.com', '{noop}passwordJonny'),
       ('Ket', 'kety@gmail.com', '{noop}passwordKety'),
       ('leo', 'Leonard@gmail.com', '{noop}passwordLeon');

INSERT INTO restaurant(name)
values ('Bear Grizzly'),
       ('Meat Home');

INSERT INTO dishes (name, price, restaurant_id, added, enabled)
VALUES ('borscht', 40, 10000, '2020-04-10 10:00:00', true),
       ('cutlet', 120, 10000, '2020-04-10 10:00:00', true),
       ('compote', 20, 10000, '2020-04-10 10:00:00', true),
       ('pasta', 100, 10000, '2020-04-10 10:00:00', false),
       ('pancake', 30, 10000, '2020-04-10 10:00:00', true),
       ('soup', 120, 10001, '2020-04-10 10:00:00', false),
       ('fish', 160, 10001, '2020-04-10 10:00:00', true),
       ('potato', 100, 10001, '2020-04-10 10:00:00', true),
       ('muffin', 55, 10001, '2020-04-10 10:00:00', false);

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 10000),
       ('ADMIN', 10000),
       ('USER', 10001),
       ('USER', 10002),
       ('USER', 10003);

INSERT INTO vote (restaurant_id, user_id, date)
VALUES (10000, 10000, '2020-04-10'),
       (10000, 10000, '2020-04-09'),
       (10000, 10001, '2020-04-09'),
       (10000, 10002, '2020-04-09'),
       (10001, 10003, '2020-04-09'),
       (10001, 10000, '2021-04-26'),
       (10001, 10001, '2021-04-26'),
       (10001, 10000, '2021-04-25'),
       (10001, 10002, '2021-04-25'),
       (10000, 10000, '2021-04-24'),
       (10001, 10001, '2021-04-22'),
       (10000, 10000, '2021-04-21'),
       (10001, 10003, '2021-04-27');

INSERT INTO vote (restaurant_id, user_id)
VALUES (10000, 10001),
       (10001, 10000)
