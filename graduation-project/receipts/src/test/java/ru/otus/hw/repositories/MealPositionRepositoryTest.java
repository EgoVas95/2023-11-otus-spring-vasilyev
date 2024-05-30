package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.MealPosition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с позициями приёма пищи")
@DataJpaTest
class MealPositionRepositoryTest {
    @Autowired
    private MealPositionRepository repository;

    @Autowired
    private TestEntityManager em;

    private static final long FIRST_ID = 1L;

    @Test
    @DisplayName("Поиск позиций приёмов пищи по приёму пищи")
    void findAllByMeal() {
        val map = getMap();
        map.keySet().forEach(key -> {
           val expected = map.get(key);
           assertThat(repository.findAllByMealId(key))
                   .usingRecursiveComparison()
                   .isEqualTo(expected);
        });
    }

    @Test
    @DisplayName("Поиск позиции приёма пищи по id")
    void findById() {
        val expected = getDb();
        for (MealPosition expect : expected) {
            val repositoryById = repository.findById(expect.getId());
            assertThat(repositoryById).isPresent().get()
                    .usingRecursiveComparison().isEqualTo(expect);
        }
    }

    @Test
    @DisplayName("Поиск полного списка позиций приёмов пищи")
    void findAll() {
        val expected = getDb();
        val repositoryVal = repository.findAll();

        assertThat(repositoryVal).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Сохранение позиции приёма пищи")
    void create() {
        val current = em.find(MealPosition.class, FIRST_ID);
        val added = new MealPosition(current.getId(), current.getMeal(),
                current.getServing(), new BigDecimal(11));
        em.merge(added);

        repository.save(added);
        em.detach(added);

        assertThat(added.getId()).isNotNull();

        val find = em.find(MealPosition.class, added.getId());
        assertThat(find)
                .usingRecursiveComparison()
                .isEqualTo(added);
    }

    @Test
    @DisplayName("Изменение позиции приёма пищи")
    void update() {
        val current = em.find(MealPosition.class, FIRST_ID);
        val updated = new MealPosition(current.getId(), current.getMeal(),
                current.getServing(), new BigDecimal(11));

        assertThat(current).usingRecursiveComparison()
                .isNotEqualTo(updated);

        val returnVal = repository.save(updated);
        assertThat(returnVal).isNotNull()
                .matches(model -> model.getId() != null)
                .usingRecursiveComparison()
                .isEqualTo(updated);

        val find = em.find(MealPosition.class, returnVal.getId());
        assertThat(find).usingRecursiveComparison()
                .isEqualTo(returnVal);
    }

    private List<MealPosition> getDb() {
        return IntStream.range(1, 15).boxed()
                .map(id -> em.find(MealPosition.class, id))
                .toList();
    }

    private Map<Long, List<MealPosition>> getMap() {
        Map<Long, List<MealPosition>> result = new HashMap<>();
        getDb().forEach(obj -> {
            val meal = obj.getMeal().getId();
            if(!result.containsKey(meal)) {
                result.put(meal, new ArrayList<>());
            }
            result.get(meal).add(obj);
        });
        return result;
    }
}
