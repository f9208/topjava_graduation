ALTER SEQUENCE USER_SEQ RESTART WITH 10000;

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', '{noop}12345'),
       ('Jonny', 'jonny@gmail.com', '{noop}passwordJonny'),
       ('Ket', 'kety@gmail.com', '{noop}passwordKety'),
       ('leo', 'leonard@gmail.com', '{noop}12345');