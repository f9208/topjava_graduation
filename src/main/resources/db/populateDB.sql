ALTER SEQUENCE USER_SEQ RESTART WITH 10000;
ALTER SEQUENCE DISH_SEQ RESTART WITH 10000;
ALTER SEQUENCE RESTAURANT_SEQ RESTART WITH 10000;
ALTER SEQUENCE VOTE_SEQ RESTART WITH 10000;
ALTER SEQUENCE IMAGE_SEQ RESTART WITH 1;

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', '{noop}12345'),
       ('Jonny', 'jonny@gmail.com', '{noop}passwordJonny'),
       ('Ket', 'kety@gmail.com', '{noop}passwordKety'),
       ('leo', 'leonard@gmail.com', '{noop}12345');

INSERT INTO image_labels(name, link_reduced)
VALUES ('Cone wood', '/resources/pic/cone.JPG'),
       ('Blueberry swamp', '/resources/pic/blueberry_on_table.JPG'),
       ('Vegetarian heaven', '/resources/pic/grass.JPG'),
       ('Village bar', '/resources/pic/onion_in_box.JPG'),
       ('Cafe-bar', '/resources/pic/strawberry.JPG');

INSERT INTO restaurant(name, image_id, description, enabled)
values ('Cone wood', 1, 'Маленький ресторанчик в лесу', true),
       ('Blueberry swamp', 2, 'маленький ресторанчик на болоте', true),
       ('Vegetarian heaven', 3, 'рай для вегетарианца', false),
       ('Village bar', 4, 'деревенский кабак в центральном урюпинске', true),
       ('Cafe-bar', 5, 'коктейль-бар', false);

INSERT INTO dishes (name, price, restaurant_id)
VALUES ('borscht', 40, 10000),
       ('cutlet', 120, 10000),
       ('compote', 20, 10000),
       ('pasta', 100, 10000),
       ('pancake', 30, 10000),
       ('soup', 120, 10001),
       ('fish', 160, 10001),
       ('potato', 100, 10001),
       ('muffin', 55, 10001);

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 10000),
       ('ADMIN', 10000),
       ('USER', 10001),
       ('USER', 10002),
       ('USER', 10003);

INSERT INTO vote (restaurant_id, user_id, day)
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

-- populate vote now()
INSERT INTO vote (restaurant_id, user_id)
VALUES (10000, 10001),
       (10001, 10000),
       (10001, 10002);

