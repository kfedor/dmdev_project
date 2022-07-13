CREATE DATABASE project_write_read;

CREATE TABLE IF NOT EXISTS user_type
(
    id   SERIAL PRIMARY KEY,
    type VARCHAR(128) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS user_details
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(128)        NOT NULL,
    last_name  VARCHAR(128)        NOT NULL,
    email      VARCHAR(128) UNIQUE NOT NULL,
    age        INT,
    gender     VARCHAR(128)        NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    id              SERIAL PRIMARY KEY,
    login           VARCHAR(128) UNIQUE NOT NULL,
    password        VARCHAR(128)        NOT NULL,
    user_type_id    INT REFERENCES user_type (id),
    user_details_id INT REFERENCES user_details (id)
);

CREATE TABLE IF NOT EXISTS text_style
(
    id         SERIAL PRIMARY KEY,
    text_style VARCHAR(128) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS text
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(128)   NOT NULL,
    text     VARCHAR UNIQUE NOT NULL,
    pages    INT            NOT NULL,
    style_id INT REFERENCES text_style (id)
);

CREATE TABLE IF NOT EXISTS text_rating
(
    text_id INT REFERENCES text (id),
    rating  NUMERIC
);

CREATE TABLE IF NOT EXISTS comment
(
    id       SERIAL PRIMARY KEY,
    text     VARCHAR(8000) NOT NULL,
    text_id  INT REFERENCES text (id),
    users_id INT REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS users_text
(
    users_id INT REFERENCES users (id),
    text_id  INT REFERENCES text (id)
);
