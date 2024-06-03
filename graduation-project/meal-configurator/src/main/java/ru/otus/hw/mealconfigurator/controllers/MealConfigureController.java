package ru.otus.hw.mealconfigurator.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.mealconfigurator.model.Day;
import ru.otus.hw.mealconfigurator.model.PricePosition;
import ru.otus.hw.mealconfigurator.services.BuyMapGeneratorImpl;
import ru.otus.hw.mealconfigurator.services.DayGeneratorImpl;
import ru.otus.hw.mealconfigurator.services.DayServiceImpl;
import ru.otus.hw.mealconfigurator.services.UserServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MealConfigureController {

    private final DayGeneratorImpl dayGenerator;

    private final DayServiceImpl dayService;

    private final UserServiceImpl userService;

    private final BuyMapGeneratorImpl buyMapGenerator;

    @GetMapping("/api/meal-list/{diet_type_id}/{calories_type_id}/{day_count}")
    public List<Day> getMealsForDays(@PathVariable("diet_type_id") String dietTypeId,
                                     @PathVariable("calories_type_id") String caloriesTypeId,
                                     @PathVariable("day_count") int dayCount) {
        return dayGenerator.generate(dietTypeId, caloriesTypeId, dayCount);
    }

    @GetMapping("/api/meal-list")
    public List<Day> getMealsForUser() {
        return dayService.findAllByUserId(userService.getCurrentUserId());
    }

    @GetMapping("/api/meal-list/{date}")
    public Day getMealsOnDayForUser(@PathVariable("date") String dateStr) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(dateStr, dtf);
        return dayService.findFirstByUserIdAndDate(userService.getCurrentUserId(), date);
    }

    @GetMapping("/api/buy-list")
    public List<PricePosition> geBuyMap() {
        return buyMapGenerator.generateBuyList();
    }

    @GetMapping("/api/buy-list/{date}")
    public List<PricePosition> geBuyMap(@PathVariable("date") String dateStr) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(dateStr, dtf);
        var map = buyMapGenerator.generateBuyMapByDay(date);
        return map.keySet().stream()
                .map(serving -> new PricePosition(serving, map.get(serving)))
                .collect(Collectors.toList());
    }
}
