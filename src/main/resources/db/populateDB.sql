ALTER SEQUENCE global_seq RESTART WITH 10000;

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', '123456789'),
       ('Jonny', 'jonny@gmail.com', 'passvordJonny'),
       ('Ket', 'kety@gmail.com', 'passvordKety'),
       ('leo', 'Leonard@gmail.com', 'passvordLeon');

INSERT INTO restaurant(name)
values ('tea_home'),
       ('home_sweat_home');
--
-- INSERT INTO dishes (name, price, restaurant_id, added, enabled)
-- VALUES ('borscht', 40, 10012, '2020-04-10 10:00:00', true),
--        ('cutlet_kiev', 120, 10013, '2020-04-10 10:00:00', true),
--        ('compote', 20, 10013, '2020-04-10 10:00:00', true),
--        ('pasta', 100, 10013, '2020-04-10 10:00:00', true),
--        ('soup', 120, 10013, '2020-04-10 10:00:00', false),
--        ('fish', 160, 10013, '2020-04-10 10:00:00', true),
--        ('pancake', 30, 10013, '2020-04-10 10:00:00', true),
--        ('muffin', 55, 10012, '2020-04-10 10:00:00', false);

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 10000),
       ('ADMIN', 10000),
       ('USER', 10001),
       ('USER', 10002),
       ('USER', 10003);

-- INSERT INTO vote (restaurant_id, user_id, date)
-- VALUES (10002, 10000, '2020-04-10'),
--     (10003, 10000, '2020-04-09'),
--     (10003, 10001, '2021-04-26'),
--     (10003, 10000, '2021-04-25'),
--     (10003, 10002, '2021-04-27'),
--     (10004, 10000, '2021-04-24'),
--     (10003, 10000, '2021-04-22'),
--     (10003, 10000, '2021-04-21'),
--     (10003, 10004, '2021-04-27');