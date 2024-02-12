package ru.otus.hw.mappers;

import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.models.Genre;

public class GenreMapper {

    public static Genre toDomainObject(GenreDto dto) {
        return new Genre(dto.getId(), dto.getName());
    }

    public static GenreDto fromDomainObject(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }
}
