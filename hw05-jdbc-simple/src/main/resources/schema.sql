create table if not exist authors (
    id bigserial,
    full_name varchar(255),
    primary key (id)
);

create table if not exist genres (
    id bigserial,
    name varchar(255),
    primary key (id)
);

create table if not exist books (
    id bigserial,
    title varchar(255),
    author_id bigint references authors (id) on delete cascade,
    genre_id bigint references genres(id) on delete cascade,
    primary key (id)
);