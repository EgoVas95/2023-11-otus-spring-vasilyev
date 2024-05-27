package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Food;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с продуктами")
@DataJpaTest
class FoodRepositoryTest {
    @Autowired
    private FoodRepository repository;

    @Autowired
    private TestEntityManager em;

    private static final long FIRST_ID = 1L;

    @Test
    @DisplayName("Поиск продукта по id")
    void findById() {
        val expected = getDbFoods();
        for (Food food : expected) {
            val repositoryById = repository.findById(food.getId());
            assertThat(repositoryById).isPresent().get()
                    .usingRecursiveComparison().isEqualTo(food);
        }
    }

    @Test
    @DisplayName("Поиск полного списка продуктов")
    void findAll() {
        val expected = getDbFoods();
        val repositoryVal = repository.findAll();

        assertThat(repositoryVal).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Сохранение продукта")
    void create() {
        val addedFood = new Food(null, "New food");
        em.merge(addedFood);

        repository.save(addedFood);
        em.detach(addedFood);

        assertThat(addedFood.getId()).isNotNull();

        val find = em.find(Food.class, addedFood.getId());
        assertThat(find)
                .usingRecursiveComparison()
                .isEqualTo(addedFood);
    }

    @Test
    @DisplayName("Изменение продукта")
    void update() {
        val current = em.find(Food.class, FIRST_ID);
        val updated = new Food(current.getId(), "New name");

        assertThat(current).usingRecursiveComparison()
                .isNotEqualTo(updated);

        val returnVal = repository.save(updated);
        assertThat(returnVal).isNotNull()
                .matches(food -> food.getId() != null)
                .usingRecursiveComparison()
                .isEqualTo(updated);

        val find = em.find(Food.class, returnVal.getId());
        assertThat(find).usingRecursiveComparison()
                .isEqualTo(returnVal);
    }

    private List<Food> getDbFoods() {
        return IntStream.range(1, 10).boxed()
                .map(id -> em.find(Food.class, id))
                .toList();
    }
}
