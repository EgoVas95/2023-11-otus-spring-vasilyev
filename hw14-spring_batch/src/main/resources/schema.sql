create table if not exists authors (
                                       id integer generated by default as identity,
                                       full_name varchar(255),
    primary key (id)
    );

create table if not exists genres (
                                      id integer generated by default as identity,
                                      name varchar(255),
    primary key (id)
    );

create table if not exists books (
                                     id integer generated by default as identity,
                                     title varchar(255),
    author_id bigint references authors (id) on delete cascade,
    genre_id bigint references genres(id) on delete cascade,
    primary key (id)
    );

create table if not exists comments (
                                        id integer generated by default as identity,
                                        book_id bigint references books (id) on delete cascade,
    text varchar(255)
    );

create table  if not exists users(
                                     id integer generated by default as identity,
                                     username varchar_ignorecase(50) not null unique,
    password varchar_ignorecase(500) not null,
    enabled boolean not null
    );

create table if not exists authorities (
                                           username varchar_ignorecase(50) not null,
    authority varchar_ignorecase(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
    );

create unique index ix_auth_username on authorities (username, authority);