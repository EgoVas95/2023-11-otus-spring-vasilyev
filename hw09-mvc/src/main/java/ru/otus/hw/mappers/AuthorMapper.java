package ru.otus.hw.mappers;

import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.models.Author;

public class AuthorMapper {

    public static Author toDomainObject(AuthorDto dto) {
        return new Author(dto.getId(), dto.getFullName());
    }

    public static AuthorDto fromDomainObject(Author author) {
        return new AuthorDto(author.getId(), author.getFullName());
    }
}
