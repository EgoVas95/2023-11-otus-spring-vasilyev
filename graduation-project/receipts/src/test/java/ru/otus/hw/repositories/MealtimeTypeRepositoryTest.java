package ru.otus.hw.repositories;


import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.MealtimeType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с типами приёма пищи")
@DataJpaTest
class MealtimeTypeRepositoryTest {
    @Autowired
    private MealtimeTypeRepository repository;

    @Autowired
    private TestEntityManager em;

    private static final long FIRST_ID = 1L;

    @Test
    @DisplayName("Поиск типа приёма пищи по id")
    void findById() {
        val expected = getDb();
        for (MealtimeType obj : expected) {
            val repositoryById = repository.findById(obj.getId());
            assertThat(repositoryById).isPresent().get()
                    .usingRecursiveComparison().isEqualTo(obj);
        }
    }

    @Test
    @DisplayName("Поиск полного списка типов приёмов пищи")
    void findAll() {
        val expected = getDb();
        val repositoryVal = repository.findAll();

        assertThat(repositoryVal).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Поиск типа приёма пищи по имени")
    void findByName() {
        val map = getByName();
        for (String key : map.keySet()) {
            val repVal = repository.findByName(key);
            assertThat(repVal).isPresent().get()
                    .usingRecursiveComparison()
                    .isEqualTo(map.get(key));
        }
    }

    @Test
    @DisplayName("Сохранение типа приёма пищи")
    void create() {
        val added = new MealtimeType(null, "New type");
        em.merge(added);

        repository.save(added);
        em.detach(added);

        assertThat(added.getId()).isNotNull();

        val find = em.find(MealtimeType.class, added.getId());
        assertThat(find)
                .usingRecursiveComparison()
                .isEqualTo(added);
    }

    @Test
    @DisplayName("Изменение типа приёма пищи")
    void update() {
        val current = em.find(MealtimeType.class, FIRST_ID);
        val updated = new MealtimeType(current.getId(), "new type");

        assertThat(current).usingRecursiveComparison()
                .isNotEqualTo(updated);

        val returnVal = repository.save(updated);
        assertThat(returnVal).isNotNull()
                .matches(food -> food.getId() != null)
                .usingRecursiveComparison()
                .isEqualTo(updated);

        val find = em.find(MealtimeType.class, returnVal.getId());
        assertThat(find).usingRecursiveComparison()
                .isEqualTo(returnVal);
    }

    private Map<String, MealtimeType> getByName() {
        Map<String, MealtimeType> result = new HashMap<>();
        getDb().forEach(type -> result.put(type.getName(), type));
        return result;
    }

    private List<MealtimeType> getDb() {
        return IntStream.range(1, 5).boxed()
                .map(id -> em.find(MealtimeType.class, id))
                .toList();
    }
}
