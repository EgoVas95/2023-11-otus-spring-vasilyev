insert into authors(full_name)
values ('М.А. Булгаков'),
       ('Братья Стругацкие'),
       ('Ирвин Уэлш');

insert into genres(name)
values ('Классика'),
       ('Научная фантастика'),
       ('Нью вейв');

insert into books(title, author_id, genre_id)
values ('Роковые яйца', 1, 1),
       ('Малыш', 2, 2),
       ('Эйсид хаус', 3, 3);

insert into comments(text, book_id)
values ('Не читал', 1),
       ('В целом - норм', 1),
       ('Мне не очень', 1),
       ('Ох, люблю этих писателей', 2),
       ('Становится жутко после прочтения', 3);

insert into users(username, password, enabled)
values ('user', '93e74353855c4cfc1b68b3732f81e1f6e73896eddfe705cbbf74c0e7c2034b8dc110d84be216bd48cbacce84a240c54e',
        true),
       ('admin', '92c4f87bfc1f7fffa4bb0bf9edbaaf86543e2ca2d3068c46965c0ca5ba0b590c5d443b3dfc9fcbf0bdb20cd3600dda80',
        true);

insert into authorities(username, authority)
values ('admin', 'ROLE_ADMIN'),
       ('admin', 'ROLE_USER'),
       ('user', 'ROLE_USER');