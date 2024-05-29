package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.CaloriesType;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с типами калоража")
@DataJpaTest
public class CaloriesRepositoryTest {
    @Autowired
    private CaloriesTypeRepository repository;

    @Autowired
    private TestEntityManager em;

    private static final long FIRST_ID = 1L;

    @Test
    @DisplayName("Поиск типа калоража по id")
    void findById() {
        val expected = getDb();
        for (CaloriesType expect : expected) {
            val repositoryById = repository.findById(expect.getId());
            assertThat(repositoryById).isPresent().get()
                    .usingRecursiveComparison().isEqualTo(expect);
        }
    }

    @Test
    @DisplayName("Поиск полного списка типа калоража")
    void findAll() {
        val expected = getDb();
        val repositoryVal = repository.findAll();

        assertThat(repositoryVal).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Сохранение типа калоража")
    void create() {
        val added = new CaloriesType(null, new BigDecimal(15));
        em.merge(added);

        repository.save(added);
        em.detach(added);

        assertThat(added.getId()).isNotNull();

        val find = em.find(CaloriesType.class, added.getId());
        assertThat(find)
                .usingRecursiveComparison()
                .isEqualTo(added);
    }

    @Test
    @DisplayName("Изменение типа калоража")
    void update() {
        val current = em.find(CaloriesType.class, FIRST_ID);
        val updated = new CaloriesType(current.getId(), new BigDecimal(15));

        assertThat(current).usingRecursiveComparison()
                .isNotEqualTo(updated);

        val returnVal = repository.save(updated);
        assertThat(returnVal).isNotNull()
                .matches(model -> model.getId() != null)
                .usingRecursiveComparison()
                .isEqualTo(updated);

        val find = em.find(CaloriesType.class, returnVal.getId());
        assertThat(find).usingRecursiveComparison()
                .isEqualTo(returnVal);
    }

    private List<CaloriesType> getDb() {
        return IntStream.range(1, 5).boxed()
                .map(id -> em.find(CaloriesType.class, id))
                .toList();
    }
}
