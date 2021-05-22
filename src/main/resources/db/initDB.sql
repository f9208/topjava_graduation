DROP TABLE dishes IF EXISTS;
DROP TABLE users IF EXISTS cascade;
DROP TABLE vote IF EXISTS;
DROP TABLE restaurant IF EXISTS;
DROP TABLE user_roles IF EXISTS;
DROP SEQUENCE USER_SEQ IF EXISTS;
DROP SEQUENCE DISH_SEQ IF EXISTS;
DROP SEQUENCE RESTAURANT_SEQ IF EXISTS;
DROP SEQUENCE VOTE_SEQ IF EXISTS;

CREATE SEQUENCE USER_SEQ AS INTEGER START WITH 10000;
CREATE SEQUENCE DISH_SEQ AS INTEGER START WITH 10000;
CREATE SEQUENCE RESTAURANT_SEQ AS INTEGER START WITH 10000;
CREATE SEQUENCE VOTE_SEQ AS INTEGER START WITH 10000;

CREATE TABLE users
(
    id         INTEGER GENERATED BY DEFAULT AS SEQUENCE USER_SEQ PRIMARY KEY,
    name       VARCHAR(255)            NOT NULL,
    email      VARCHAR(255)            NOT NULL,
    password   VARCHAR(255)            NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL
);
CREATE
    UNIQUE INDEX users_unique_email_idx
    ON users (email);

CREATE TABLE restaurant
(
    id   INTEGER GENERATED BY DEFAULT AS SEQUENCE RESTAURANT_SEQ PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
CREATE
    UNIQUE INDEX restaurant_unique_name_idx
    ON restaurant (name);

CREATE TABLE dishes
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE DISH_SEQ PRIMARY KEY,
    name          VARCHAR(255)                   NOT NULL,
    price         INTEGER                        NOT NULL,
    restaurant_id INTEGER                        NOT NULL,
    added         TIMESTAMP DEFAULT CURRENT_DATE NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX dish_unique_restaurant_dishName_idx
    ON dishes (restaurant_id, name);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE vote
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE VOTE_SEQ PRIMARY KEY,
    day          DATE DEFAULT CURRENT_DATE NOT NULL,
    restaurant_id INTEGER                   NOT NULL,
    user_id       INTEGER                   NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX votes_unique_user_date_idx
    ON vote (user_id, day);