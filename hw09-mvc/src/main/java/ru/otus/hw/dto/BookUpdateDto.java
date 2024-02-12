package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookUpdateDto {
    @NotNull
    private Long id;

    @NotBlank(message = "Book title can't be null")
    @Size(min = 1, max = 100, message = "Book title should be with size from 1 to 100 symbols")
    private String title;

    @NotNull(message = "Book author can't be null")
    private AuthorDto author;

    @NotNull(message = "Book genre can't be null")
    private GenreDto genre;

    public static BookUpdateDto fromBookDto(BookDto bookDto) {
        return new BookUpdateDto(bookDto.getId(), bookDto.getTitle(),
                bookDto.getAuthor(), bookDto.getGenre());
    }
}
