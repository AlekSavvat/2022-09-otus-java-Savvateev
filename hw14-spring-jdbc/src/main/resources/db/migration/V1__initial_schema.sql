create table client
(
    id           bigserial   not null primary key,
    name         varchar(50) not null
);

create table phone
(
    id          bigserial   not null primary key,
    number        varchar(50) not null,
    client_id   bigint      not null references client (id)
);


create table address
(
    id          bigserial   not null primary key,
    street        varchar(50) not null,
    client_id   bigint      not null references client (id)
);


insert into client (name)
values ('Путин'),
       ('Байден'),
       ('Зеленский');

insert into address (street, client_id)
values ('Россия', 1),
       ('Америка', 2),
       ('Украина', 3);

insert into phone (number, client_id)
VALUES ('+1 234 56 78',  1),
       ('+9 876 54 32',  2),
       ('+10 234 12 98', 3);