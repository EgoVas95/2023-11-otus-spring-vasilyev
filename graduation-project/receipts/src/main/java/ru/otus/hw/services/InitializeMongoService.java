package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.CaloriesType;
import ru.otus.hw.models.DietType;
import ru.otus.hw.models.Food;
import ru.otus.hw.models.Mealtime;
import ru.otus.hw.models.Receipt;
import ru.otus.hw.models.ReceiptPosition;
import ru.otus.hw.models.Serving;
import ru.otus.hw.repositories.CaloriesTypeRepository;
import ru.otus.hw.repositories.DietTypeRepository;
import ru.otus.hw.repositories.FoodRepository;
import ru.otus.hw.repositories.MealtimeRepository;
import ru.otus.hw.repositories.ReceiptRepository;
import ru.otus.hw.repositories.ServingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class InitializeMongoService {
    private final List<CaloriesType> caloriesTypeList = new ArrayList<>(4);

    private final List<DietType> dietTypeList = new ArrayList<>(3);

    private final List<Mealtime> mealtimeList = new ArrayList<>(3);

    private final List<Food> foodList = new ArrayList<>(60);

    private final List<Serving> servingList = new ArrayList<>(120);

    private final CaloriesTypeRepository caloriesTypeRepository;

    private final DietTypeRepository dietTypeRepository;

    private final MealtimeRepository mealtimeRepository;

    private final FoodRepository foodRepository;

    private final ServingRepository servingRepository;

    private final ReceiptRepository receiptRepository;

    public void init() {
        addCaloriesTypeList();
        addDietTypeList();
        addMealtimeList();
        addFoodList();
        addServingList();
        addReceipts();
    }

    public void addCaloriesTypeList() {
        caloriesTypeList.add(caloriesTypeRepository.save(new CaloriesType(null, 1600L)));
        caloriesTypeList.add(caloriesTypeRepository.save(new CaloriesType(null, 1800L)));
        caloriesTypeList.add(caloriesTypeRepository.save(new CaloriesType(null, 2000L)));
        caloriesTypeList.add(caloriesTypeRepository.save(new CaloriesType(null, 2400L)));
    }

    public void addDietTypeList() {
        dietTypeList.add(dietTypeRepository.save(new DietType(null, "Высокобелковая")));
        dietTypeList.add(dietTypeRepository.save(new DietType(null, "Среднесбалансированная")));
        dietTypeList.add(dietTypeRepository.save(new DietType(null, "Кето")));
    }

    public void addMealtimeList() {
        mealtimeList.add(mealtimeRepository.save(new Mealtime(null, "Завтрак")));
        mealtimeList.add(mealtimeRepository.save(new Mealtime(null, "Обед")));
        mealtimeList.add(mealtimeRepository.save(new Mealtime(null, "Ужин")));
    }

    public void addFoodList() {
        for (int idx = 0; idx < 60; idx++) {
            foodList.add(foodRepository.save(new Food(null, "Продукт №%d".formatted((idx + 1)))));
        }
    }

    public void addServingList() {
        for (Food food : foodList) {
            Random random = new Random();
            Long calories = random.nextLong(10, 200);
            servingList.add(servingRepository
                    .save(new Serving(null, "шт.", food, calories)));
            servingList.add(servingRepository
                    .save(new Serving(null, "грамм", food, calories)));
        }
    }

    public void addReceipts() {
        Random random = new Random();

        for (int idx = 0; idx < 130; idx++) {
            int positionSize = random.nextInt(1, 5);
            var positionList = new ArrayList<ReceiptPosition>(positionSize);
            for (int positionIdx = 0; positionIdx < positionSize; positionIdx++) {
                positionList.add(new ReceiptPosition(
                        servingList.get(random.nextInt(servingList.size())),
                        random.nextLong(1L, 200L)));
            }

            receiptRepository.save(
                    new Receipt(null,
                            "Рецепт %d".formatted(idx),
                            caloriesTypeList.get(random.nextInt(caloriesTypeList.size())),
                            dietTypeList.get(random.nextInt(dietTypeList.size())),
                            mealtimeList.get(random.nextInt(mealtimeList.size())),
                            positionList));
        }
    }

}
