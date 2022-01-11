DROP TABLE if exists dishes;
DROP TABLE IF EXISTS users cascade;
DROP TABLE IF EXISTS vote;
DROP TABLE IF EXISTS restaurant;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS image_labels;
DROP SEQUENCE IF EXISTS USER_SEQ;
DROP SEQUENCE IF EXISTS DISH_SEQ;
DROP SEQUENCE IF EXISTS RESTAURANT_SEQ;
DROP SEQUENCE IF EXISTS VOTE_SEQ;
DROP SEQUENCE IF EXISTS IMAGE_SEQ;

CREATE SEQUENCE USER_SEQ AS INTEGER START WITH 10000;
CREATE SEQUENCE DISH_SEQ AS INTEGER START WITH 10000;
CREATE SEQUENCE RESTAURANT_SEQ AS INTEGER START WITH 10000;
CREATE SEQUENCE VOTE_SEQ AS INTEGER START WITH 10000;
CREATE SEQUENCE IMAGE_SEQ AS INTEGER START WITH 1;

CREATE TABLE users
(
    id         BIGINT    default nextval('USER_SEQ') PRIMARY KEY,
    name       VARCHAR(255)            NOT NULL,
    email      VARCHAR(255)            NOT NULL,
    password   VARCHAR(255)            NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL
);
CREATE
    UNIQUE INDEX users_unique_email_idx
    ON users (email);

CREATE TABLE image_labels
(
    id           INTEGER default nextval('IMAGE_SEQ') PRIMARY KEY,
    name         VARCHAR(255),
    link_origin  VARCHAR(1023) unique,
    link_reduced VARCHAR(1023) NOT NULL unique
);

CREATE TABLE restaurant
(
    id          INTEGER default nextval('RESTAURANT_SEQ') PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    image_id    INTEGER,
    description VARCHAR(1000),
    enabled     BOOLEAN default TRUE,
    FOREIGN KEY (image_id) REFERENCES image_labels (id) ON DELETE CASCADE
);
CREATE
    UNIQUE INDEX restaurant_unique_name_idx
    ON restaurant (name);

CREATE TABLE dishes
(
    id            INTEGER   default nextval('DISH_SEQ') PRIMARY KEY,
    name          VARCHAR(255)                   NOT NULL,
    price         INTEGER                        NOT NULL,
    restaurant_id INTEGER                        NOT NULL,
    day           TIMESTAMP DEFAULT CURRENT_DATE NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX dish_unique_restaurant_dishName_idx
    ON dishes (restaurant_id, name, day);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE vote
(
    id            INTEGER default nextval('VOTE_SEQ') PRIMARY KEY,
    day           DATE    DEFAULT CURRENT_DATE NOT NULL,
    restaurant_id INTEGER                      NOT NULL,
    user_id       INTEGER                      NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX votes_unique_user_date_idx
    ON vote (user_id, day);

CREATE UNIQUE INDEX image_label_unique_link_idx
    ON image_labels (link_origin, link_reduced);