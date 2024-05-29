package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.DietType;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с типами диет")
@DataJpaTest
public class DietRepositoryTest {
    @Autowired
    private DietTypeRepository repository;

    @Autowired
    private TestEntityManager em;

    private static final long FIRST_ID = 1L;

    @Test
    @DisplayName("Поиск типа диеты по id")
    void findById() {
        val expected = getDb();
        for (DietType expect : expected) {
            val repositoryById = repository.findById(expect.getId());
            assertThat(repositoryById).isPresent().get()
                    .usingRecursiveComparison().isEqualTo(expect);
        }
    }

    @Test
    @DisplayName("Поиск полного списка типа диет")
    void findAll() {
        val expected = getDb();
        val repositoryVal = repository.findAll();

        assertThat(repositoryVal).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Сохранение типа диеты")
    void create() {
        val added = new DietType(null, "New diet");
        em.merge(added);

        repository.save(added);
        em.detach(added);

        assertThat(added.getId()).isNotNull();

        val find = em.find(DietType.class, added.getId());
        assertThat(find)
                .usingRecursiveComparison()
                .isEqualTo(added);
    }

    @Test
    @DisplayName("Изменение типа диеты")
    void update() {
        val current = em.find(DietType.class, FIRST_ID);
        val updated = new DietType(current.getId(), "New name");

        assertThat(current).usingRecursiveComparison()
                .isNotEqualTo(updated);

        val returnVal = repository.save(updated);
        assertThat(returnVal).isNotNull()
                .matches(model -> model.getId() != null)
                .usingRecursiveComparison()
                .isEqualTo(updated);

        val find = em.find(DietType.class, returnVal.getId());
        assertThat(find).usingRecursiveComparison()
                .isEqualTo(returnVal);
    }

    private List<DietType> getDb() {
        return IntStream.range(1, 3).boxed()
                .map(id -> em.find(DietType.class, id))
                .toList();
    }
}
