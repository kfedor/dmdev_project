CREATE
DATABASE project_write_read;

CREATE TABLE user_type
(
    id   SERIAL PRIMARY KEY,
    type VARCHAR(128) NOT NULL
);

CREATE TABLE gender
(
    id     SERIAL PRIMARY KEY,
    gender VARCHAR(128) NOT NULL
);

CREATE TABLE users
(
    id              SERIAL PRIMARY KEY,
    login           VARCHAR(128) UNIQUE NOT NULL,
    password        VARCHAR(128)        NOT NULL,
    user_type_id    INT REFERENCES user_type (id),
    user_details_id INT REFERENCES user_details (id)
);

CREATE TABLE user_details
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(128)        NOT NULL,
    last_name  VARCHAR(128)        NOT NULL,
    email      VARCHAR(128) UNIQUE NOT NULL,
    age        INT,
    gender_id  INT REFERENCES gender (id)
);

CREATE TABLE text_style
(
    id         SERIAL PRIMARY KEY,
    text_style VARCHAR(128) NOT NULL
);


CREATE TABLE text
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(128) NOT NULL,
    pages    INT          NOT NULL,
    style_id INT REFERENCES text_style (id)
);
CREATE TABLE text_rating
(
    text_id INT REFERENCES text(id),
    rating NUMERIC
);

CREATE TABLE comment
(
    id           SERIAL PRIMARY KEY,
    comment_text VARCHAR(8000) NOT NULL,
    text_id      INT REFERENCES text (id),
    users_id     INT REFERENCES users (id)
);

CREATE TABLE users_text
(
    users_id INT REFERENCES users (id),
    text_id  INT REFERENCES text (id)
);

