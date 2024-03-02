package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import reactor.test.StepVerifier;
import ru.otus.hw.models.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для работы с авторами")
@DataMongoTest
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final long FIRST_AUTHOR_ID = 1L;

    @BeforeEach
    public void initialize() {
        mongoTemplate.save(new Author(FIRST_AUTHOR_ID, "Author"));
    }

    @DisplayName("должен найти корректный жанр по id")
    @Test
    void findById() {
        val expectedGenre = mongoTemplate.findById(FIRST_AUTHOR_ID, Author.class);

        StepVerifier
                .create(authorRepository.findById(FIRST_AUTHOR_ID))
                        .assertNext(author -> assertThat(author)
                                .usingRecursiveComparison().isEqualTo(expectedGenre))
                .expectComplete()
                .verify();
    }
}
