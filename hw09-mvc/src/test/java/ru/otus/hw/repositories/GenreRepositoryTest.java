package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для работы с комментариями ")
@DataJpaTest
public class GenreRepositoryTest {
    @Autowired
    private GenreRepository jpaGenreRepository;

    @Autowired
    private TestEntityManager em;

    private static final long FIRST_GENRE_ID = 1L;

    @DisplayName("должен найти корректный жанр по id")
    @Test
    void findById() {
        val optionalActualGenre = jpaGenreRepository.findById(FIRST_GENRE_ID);
        val expectedGenre = em.find(Genre.class, FIRST_GENRE_ID);

        assertThat(optionalActualGenre).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedGenre);
    }
}
