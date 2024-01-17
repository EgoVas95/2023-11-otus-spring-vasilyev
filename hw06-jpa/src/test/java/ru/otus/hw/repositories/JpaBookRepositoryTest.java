package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для работы с книгами ")
@DataJpaTest
@Import({JpaBookRepository.class})
class JpaBookRepositoryTest {

    private static final long FIRST_AUTHOR_ID = 1L;
    private static final long FIRST_GENRE_ID = 1L;
    private static final long FIRST_BOOK_ID = 1L;

    @Autowired
    private JpaBookRepository bookRepositoryJpa;

    @Autowired
    private TestEntityManager em;


    @DisplayName("должен загружать книгу по id")
    @ParameterizedTest
    @MethodSource("getDbBooks")
    void shouldReturnCorrectBookById(Book expectedBook) {
        var actualBook = bookRepositoryJpa.findById(expectedBook.getId());
        assertThat(actualBook).isPresent()
                .get()
                .isEqualTo(expectedBook);
    }

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldReturnCorrectBooksList() {
        var actualBooks = bookRepositoryJpa.findAll();
        var expectedBooks = getDbBooks();

        assertThat(actualBooks).containsExactlyElementsOf(expectedBooks);
        actualBooks.forEach(System.out::println);
    }

    @DisplayName("должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        val author = em.find(Author.class, FIRST_AUTHOR_ID);
        val genre = em.find(Genre.class, FIRST_GENRE_ID);
        val insertBook = new Book(0, "BookTitle_10500", author, genre);

        bookRepositoryJpa.save(insertBook);
        assertThat(insertBook.getId()).isGreaterThan(0);

        val findBook = em.find(Book.class, insertBook.getId());
        assertThat(findBook)
                .usingRecursiveComparison()
                .isEqualTo(insertBook);
    }

    @DisplayName("должен сохранять измененную книгу")
    @Test
    void shouldSaveUpdatedBook() {
        val author = em.find(Author.class, FIRST_AUTHOR_ID);
        val genre = em.find(Genre.class, FIRST_GENRE_ID);

        var expectedBook = new Book(FIRST_BOOK_ID, "BookTitle_10500", author, genre);
        var currentBook = em.find(Book.class, expectedBook.getId());

        assertThat(currentBook)
                .usingRecursiveComparison()
                .isNotEqualTo(expectedBook);

        var returnedBook = bookRepositoryJpa.save(expectedBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        var findBook = em.find(Book.class, returnedBook.getId());
        assertThat(findBook)
                .usingRecursiveComparison()
                .isEqualTo(returnedBook);
    }

    @DisplayName("должен удалять книгу по id ")
    @Test
    void shouldDeleteBook() {
        Book firstBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(firstBook).isNotNull();
        bookRepositoryJpa.deleteById(FIRST_BOOK_ID);
        Book notFindedBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(notFindedBook).isNull();
    }

    private static List<Author> getDbAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Author(id, "Author_" + id))
                .toList();
    }

    private static List<Genre> getDbGenres() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Genre(id, "Genre_" + id))
                .toList();
    }

    private static List<Book> getDbBooks(List<Author> dbAuthors, List<Genre> dbGenres) {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Book(id, "BookTitle_" + id, dbAuthors.get(id - 1), dbGenres.get(id - 1)))
                .toList();
    }

    private static List<Book> getDbBooks() {
        var dbAuthors = getDbAuthors();
        var dbGenres = getDbGenres();
        return getDbBooks(dbAuthors, dbGenres);
    }
}