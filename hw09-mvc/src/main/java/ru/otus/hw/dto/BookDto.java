package ru.otus.hw.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.hw.models.Book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
public class BookDto {
    private Long id;

    @NotBlank(message = "Book title can't be null")
    @Size(min = 1, max = 100, message = "Book title should be with size from 1 to 100 symbols")
    private String title;

    @NotNull(message = "Book author can't be null")
    private AuthorDto author;

    @NotNull(message = "Book genre can't be null")
    private GenreDto genre;

    public Book toDomainObject() {
        return new Book(id, title, author.toDomainObject(), genre.toDomainObject());
    }

    public static BookDto fromDomainObject(Book book) {
        return new BookDto(book.getId(), book.getTitle(),
                AuthorDto.fromDomainObject(book.getAuthor()),
                GenreDto.fromDomainObject(book.getGenre()));
    }
}
