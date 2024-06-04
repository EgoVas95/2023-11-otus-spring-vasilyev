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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MealConfigureController {

    private final DayGeneratorImpl dayGenerator;

    private final DayServiceImpl dayService;

    private final BuyMapGeneratorImpl buyMapGenerator;

    @GetMapping("/api/meal-list/{username}/{diet_type_id}/{calories_type_id}/{day_count}")
    public List<Day> getMealsForDays(@PathVariable("username") String username,
                                     @PathVariable("diet_type_id") String dietTypeId,
                                     @PathVariable("calories_type_id") String caloriesTypeId,
                                     @PathVariable("day_count") int dayCount) {
        return dayGenerator.generate(username, dietTypeId, caloriesTypeId, dayCount);
    }

    @GetMapping("/api/meal-list/{username}")
    public List<Day> getMealsForUser(@PathVariable("username") String username) {
        return dayService.findAllByUserId(username);
    }

    @GetMapping("/api/meal-list/{username}/{date}")
    public Day getMealsOnDayForUser(@PathVariable("username") String username,
                                    @PathVariable("date") String dateStr) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(dateStr, dtf);
        return dayService.findFirstByUserIdAndDate(username, date);
    }

    @GetMapping("/api/buy-list/{username}")
    public List<PricePosition> getBuyMap(@PathVariable("username") String username) {
        return buyMapGenerator.generateBuyList(username);
    }

    @GetMapping("/api/buy-list/{username}/{date}")
    public List<PricePosition> getBuyMap(@PathVariable("username") String username,
                                         @PathVariable("date") String dateStr) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(dateStr, dtf);
        var map = buyMapGenerator.generateBuyMapByDay(username, date);
        return map.keySet().stream()
                .map(serving -> new PricePosition(serving, map.get(serving)))
                .collect(Collectors.toList());
    }
}
