insert into authors(full_name)
    values ('М.А. Булгаков'), ('Братья Стругацкие'), ('Ирвин Уэлш');

insert into genres(name)
    values ('Классика'), ('Научная фантастика'), ('Нью вейв');

insert into books(title, author_id, genre_id)
    values ('Роковые яйца', 1, 1), ('Малыш', 2, 2), ('Эйсид хаус', 3, 3);

insert into comments(text, book_id)
    values ('Не читал', 1),
    ('В целом - норм', 1),
    ('Мне не очень', 1),
    ('Ох, люблю этих писателей', 2),
    ('Становится жутко после прочтения', 3);