CREATE TABLE users
(
    username    varchar(50)     PRIMARY KEY,
    password    varchar(200)    not null,
    enabled     boolean         not null
);

CREATE TABLE authorities
(
    id          SERIAL          PRIMARY KEY ,
    username    varchar(50)     not null,
    authorities varchar(50)     not null,
    CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username) ON DELETE CASCADE /* Khi xoa user thi authorities cun xoa theo */
)

CREATE TABLE customer
(
    id          SERIAL          PRIMARY KEY ,
    username    varchar(50)     not null,
    password    varchar(200)    not null,
    role        varchar(500)    not null
)

INSERT INTO customer (id, username, password, role)
VALUES (1, 'lam1', 'password', 'admin')
