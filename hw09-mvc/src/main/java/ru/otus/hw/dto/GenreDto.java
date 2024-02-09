package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import ru.otus.hw.models.Genre;

@Data
@AllArgsConstructor
@ToString
public class GenreDto {
    private Long id;

    private String name;

    public Genre toDomainObject() {
        return new Genre(id, name);
    }

    public static GenreDto fromDomainObject(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }
}
