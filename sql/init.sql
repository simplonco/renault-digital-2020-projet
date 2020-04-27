CREATE DATABASE cars;

USE cars;

DROP TABLE if exists cars cascade;

DROP TABLE if exists roles cascade;

DROP TABLE if exists users cascade;

DROP TABLE if exists user_roles cascade;

CREATE TABLE cars
(
    id    integer      NOT NULL,
    brand VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE roles
(
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (name)
);

CREATE TABLE users
(
    username VARCHAR(255) NOT NULL,
    enabled  BOOLEAN,
    password VARCHAR(255),
    PRIMARY KEY (username)
);

CREATE TABLE user_roles
(
    user_username VARCHAR(255) NOT NULL,
    role_name     VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_username) REFERENCES users (username),
    FOREIGN KEY (role_name) REFERENCES roles (name)
);
