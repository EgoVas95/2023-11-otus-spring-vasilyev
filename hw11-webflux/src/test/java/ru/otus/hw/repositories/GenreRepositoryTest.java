package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import reactor.test.StepVerifier;
import ru.otus.hw.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для работы с комментариями ")
@DataMongoTest
public class GenreRepositoryTest {
    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final long FIRST_GENRE_ID = 1L;

    @BeforeEach
    public void initialize() {
        mongoTemplate.save(new Genre(FIRST_GENRE_ID, "Genre"));
    }

    @DisplayName("должен найти корректный жанр по id")
    @Test
    void findById() {
        val expectedGenre = mongoTemplate.findById(FIRST_GENRE_ID, Genre.class);

        StepVerifier.create(genreRepository.findById(FIRST_GENRE_ID))
                .assertNext(genre -> assertThat(genre)
                        .usingRecursiveComparison().isEqualTo(expectedGenre))
                .expectComplete()
                .verify();
    }
}
