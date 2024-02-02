package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе MongoDB для работы с книгами ")
@DataMongoTest
class BookRepositoryTest {

    private static final String FIRST_AUTHOR_ID = "1";
    private static final String FIRST_GENRE_ID = "1";
    private static final String FIRST_BOOK_ID = "1";

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void initialize() {
        final Author bulgakov = new Author(FIRST_AUTHOR_ID, "М.А. Булгаков");
        final Genre classic = new Genre(FIRST_GENRE_ID, "Классика");
        mongoTemplate.save(new Book(FIRST_BOOK_ID, "Роковые яйца", bulgakov, classic));

        final Author strugackie = new Author("2", "Братья Стругацкие");
        final Genre sciFi = new Genre("2", "Научная фантастика");
        mongoTemplate.save(new Book("2", "Малыш", strugackie, sciFi));
    }

    @DisplayName("должен загружать книгу по id")
    @Test
    void shouldReturnCorrectBookById() {
        var expectedBooks = getDbBooks();
        for (Book expectedBook : expectedBooks) {
            var actualBookOption = bookRepository.findById(expectedBook.getId());

            assertThat(actualBookOption).isPresent()
                    .get()
                    .usingRecursiveComparison()
                    .isEqualTo(expectedBook);
        }
    }

    @DisplayName("должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        val author = mongoTemplate.findById(FIRST_AUTHOR_ID, Author.class);
        val genre = mongoTemplate.findById(FIRST_GENRE_ID, Genre.class);
        val addedBook = new Book(null, "BookTitle_10500", author, genre);

        bookRepository.save(addedBook);
        assertThat(addedBook.getId()).isNotEmpty();

        val findBook = mongoTemplate.findById(addedBook.getId(), Book.class);
        assertThat(findBook)
                .usingRecursiveComparison()
                .isEqualTo(addedBook);
    }

    @DisplayName("должен сохранять измененную книгу")
    @Test
    void shouldSaveUpdatedBook() {
        val author = mongoTemplate.findById(FIRST_AUTHOR_ID, Author.class);
        val genre = mongoTemplate.findById(FIRST_GENRE_ID, Genre.class);

        var expectedBook = new Book(FIRST_BOOK_ID, "BookTitle_10500", author, genre);
        var currentBook = mongoTemplate.findById(expectedBook.getId(), Book.class);

        assertThat(currentBook)
                .usingRecursiveComparison()
                .isNotEqualTo(expectedBook);

        var returnedBook = bookRepository.save(expectedBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> !book.getId().isEmpty())
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expectedBook);

        var findBook = mongoTemplate.findById(returnedBook.getId(), Book.class);
        assertThat(findBook)
                .usingRecursiveComparison()
                .isEqualTo(returnedBook);
    }

    private List<Book> getDbBooks() {
        return mongoTemplate.findAll(Book.class);
    }
}