package ru.otus.hw.mappers;

import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.models.Book;

public class BookMapper {

    public static Book toDomainObject(BookDto dto) {
        return new Book(dto.getId(), dto.getTitle(),
                AuthorMapper.toDomainObject(dto.getAuthor()),
                GenreMapper.toDomainObject(dto.getGenre()));
    }

    public static Book toDomainObject(BookCreateDto dto) {
        return new Book(null, dto.getTitle(),
                AuthorMapper.toDomainObject(dto.getAuthor()),
                GenreMapper.toDomainObject(dto.getGenre()));
    }

    public static Book toDomainObject(BookUpdateDto dto) {
        return new Book(dto.getId(), dto.getTitle(),
                AuthorMapper.toDomainObject(dto.getAuthor()),
                GenreMapper.toDomainObject(dto.getGenre()));
    }

    public static BookDto fromDomainObject(Book book) {
        return new BookDto(book.getId(), book.getTitle(),
                AuthorMapper.fromDomainObject(book.getAuthor()),
                GenreMapper.fromDomainObject(book.getGenre()));
    }
}
