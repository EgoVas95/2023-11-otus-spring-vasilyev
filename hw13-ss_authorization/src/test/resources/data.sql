insert into authors(id, full_name)
values (1, 'Author_1'), (2, 'Author_2'), (3, 'Author_3');

insert into genres(id, name)
values (1, 'Genre_1'), (2, 'Genre_2'), (3, 'Genre_3');

insert into books(title, author_id, genre_id)
values ('BookTitle_1', 1, 1), ('BookTitle_2', 2, 2), ('BookTitle_3', 3, 3);

insert into comments(book_id, text)
values (1, 'Book_1_Comment_1'), (1, 'Book_1_Comment_2'),
       (1, 'Book_1_Comment_3'), (2, 'Book_2_Comment_1'), (3, 'Book_3_Comment_1');

insert into users(username, password, enabled)
values ('user', '93e74353855c4cfc1b68b3732f81e1f6e73896eddfe705cbbf74c0e7c2034b8dc110d84be216bd48cbacce84a240c54e',
        true),
       ('admin', '92c4f87bfc1f7fffa4bb0bf9edbaaf86543e2ca2d3068c46965c0ca5ba0b590c5d443b3dfc9fcbf0bdb20cd3600dda80',
        true);

insert into authorities(username, authority)
values ('admin', 'ROLE_ADMIN'),
       ('admin', 'ROLE_USER'),
       ('user', 'ROLE_USER');
