package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import ru.otus.hw.models.Author;

@Data
@AllArgsConstructor
@ToString
public class AuthorDto {

    private Long id;

    private String fullName;

    public Author toDomainObject() {
        return new Author(id, fullName);
    }

    public static AuthorDto fromDomainObject(Author author) {
        return new AuthorDto(author.getId(), author.getFullName());
    }
}
