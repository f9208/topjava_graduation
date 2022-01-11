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
VALUES ('KFC', '/resources/pic/KFC.JPG'),
       ('Блинная Mr. Pancake', '/resources/pic/Pancake.jpg'),
       ('Бургерная The Woods', '/resources/pic/Burger.jpg'),
       ('Хинкальная', '/resources/pic/khinkali.jpg'),
       ('кафе-бар "Виктория"', '/resources/pic/strawberry.JPG');

INSERT INTO restaurant(name, image_id, description, enabled)
values ('KFC', 1,
        ' американская сеть ресторанов фастфуда общественного питания, специализирующихся на блюдах из курятины. Была основана в 1952 году Харландом Сандерсом под вывеской Kentucky Fried Chicken',
        true),
       ('Блинная Mr. Pancake', 2,
        'Маленькое семейное кафе расположенное в самом центре нашего города! Здесь всегда свежие, горячие и вкусные блины с различной начинкой! Также в меню есть горячие напитки!',
        true),
       ('Бургерная The Woods', 3,
        'The Woods Craft Bar –это истинная атмосфера мужественности. Терпкая смола, пряная хвоя, хмель и солод - вот, что отличает нас',
        false),
       ('Хинкальная', 4,
        'Где можно отведать блюда настоящей грузинской кухни? Где можно хорошо отдохнуть, получить новые впечатления, положительные эмоции? Конечно, в кафе «Хинкальная»!',
        true),
       ('кафе-бар "Виктория"', 5,
        'Бар - наливайка 24 часа! Поздно возвращаешься с работы и магазины уже закрыты? Заходи!', false);

INSERT INTO dishes (name, price, restaurant_id)
VALUES ('Cheef burger', 140, 10000),
       ('basket free', 120, 10000),
       ('Chicken nuggets', 120, 10000),
       ('Wings', 100, 10000),
       ('Coca-Cola', 50, 10000),

       ('Pancake', 80, 10001),
       ('jam', 20, 10001),
       ('green tea', 100, 10001),
       ('black tea', 55, 10001),
       ('green tea', 65, 10001),
       ('cappuccino', 120, 10001),
       ('sour cream', 50, 10001),
       ('ice cream', 150, 10001),

       ('meat burger', 500, 10002),
       ('chicken burger', 300, 10002),
       ('vegetarian burger', 400, 10002),
       ('lager', 500, 10002),
       ('black coffee', 200, 10002),
       ('light beer', 500, 10002),

       ('khinkali', 700, 10003),
       ('dumplings', 700, 10003),
       ('sour cream', 150, 10003),
       ('vodka "Absolut"', 250, 10003),
       ('sour cream', 150, 10003),
       ('roast beef', 850, 10003),

       ('beer', 150, 10004),
       ('lays', 100, 10004),
       ('pringles', 300, 10004),
       ('dumplings', 500, 10004);

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

