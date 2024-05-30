package ru.otus.hw.repositories;


import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Meal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с приёмами пищи")
@DataJpaTest
class MealRepositoryTest {
    @Autowired
    private MealRepository repository;

    @Autowired
    private TestEntityManager em;

    private static final long FIRST_ID = 1L;

    @Test
    @DisplayName("Поиск приёма пищи по набору параметров")
    void findByCustomParams() {
        val map = getMapDb();
        map.keySet().forEach(mealtimeType -> map.get(mealtimeType)
                .keySet().forEach(dietType ->
                        map.get(mealtimeType).get(dietType).keySet().forEach(caloriesType -> {
                            val expected = map.get(mealtimeType).get(dietType).get(caloriesType);
                            assertThat(repository.findAllByMealtimeTypeIdAndDietTypeIdAndCaloriesTypeId(
                                    mealtimeType, dietType, caloriesType))
                                    .usingRecursiveComparison()
                                    .isEqualTo(expected);
                        })));
    }

    @Test
    @DisplayName("Поиск типа приёма пищи по id")
    void findById() {
        val expected = getDb();
        for (Meal expect : expected) {
            val repositoryById = repository.findById(expect.getId());
            assertThat(repositoryById).isPresent().get()
                    .usingRecursiveComparison().isEqualTo(expect);
        }
    }

    @Test
    @DisplayName("Поиск полного списка приёмов пищи")
    void findAll() {
        val expected = getDb();
        val repositoryVal = repository.findAll();

        assertThat(repositoryVal).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Сохранение типа приёма пищи")
    void create() {
        val current = em.find(Meal.class, FIRST_ID);
        val added = new Meal(null, null,
                current.getDietType(), current.getCaloriesType());
        em.merge(added);

        repository.save(added);
        em.detach(added);

        assertThat(added.getId()).isNotNull();

        val find = em.find(Meal.class, added.getId());
        assertThat(find)
                .usingRecursiveComparison()
                .isEqualTo(added);
    }

    @Test
    @DisplayName("Изменение типа приёма пищи")
    void update() {
        val current = em.find(Meal.class, FIRST_ID);
        val updated = new Meal(current.getId(), null,
                current.getDietType(), current.getCaloriesType());

        assertThat(current).usingRecursiveComparison()
                .isNotEqualTo(updated);

        val returnVal = repository.save(updated);
        assertThat(returnVal).isNotNull()
                .matches(model -> model.getId() != null)
                .usingRecursiveComparison()
                .isEqualTo(updated);

        val find = em.find(Meal.class, returnVal.getId());
        assertThat(find).usingRecursiveComparison()
                .isEqualTo(returnVal);
    }

    private List<Meal> getDb() {
        return IntStream.range(1, 13).boxed()
                .map(id -> em.find(Meal.class, id))
                .toList();
    }

    private Map<Long, Map<Long, Map<Long, List<Meal>>>> getMapDb() {
        Map<Long, Map<Long, Map<Long, List<Meal>>>> map = new HashMap<>();
        getDb().forEach(match -> {
            val mealtime = match.getMealtimeType().getId();
            if (!map.containsKey(mealtime)) {
                map.put(mealtime, new HashMap<>());
            }
            val diet = match.getDietType().getId();
            if (!map.get(mealtime).containsKey(diet)) {
                map.get(mealtime).put(diet, new HashMap<>());
            }
            val calories = match.getCaloriesType().getId();
            if (!map.get(mealtime).get(diet).containsKey(calories)) {
                map.get(mealtime).get(diet).put(calories, new ArrayList<>());
            }
            map.get(mealtime).get(diet).get(calories).add(match);
        });
        return map;
    }
}
