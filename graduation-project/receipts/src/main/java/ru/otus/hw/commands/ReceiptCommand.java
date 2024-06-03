package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converter.ReceiptConverter;
import ru.otus.hw.models.Receipt;
import ru.otus.hw.models.ReceiptPosition;
import ru.otus.hw.services.calories.CaloriesTypeService;
import ru.otus.hw.services.diet.DietTypeService;
import ru.otus.hw.services.food.FoodService;
import ru.otus.hw.services.mealtime.MealtimeService;
import ru.otus.hw.services.receipt.ReceiptService;
import ru.otus.hw.services.serving.ServingService;

import java.util.Collections;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class ReceiptCommand {
    private final ReceiptService service;

    private final ReceiptConverter converter;

    private final CaloriesTypeService caloriesTypeService;

    private final MealtimeService mealtimeService;

    private final DietTypeService dietTypeService;

    private final ServingService servingService;

    private final FoodService foodService;

    @ShellMethod(value = "Get all receipts", key = "ra")
    public String findAll() {
        return service.findAll().stream()
                .map(converter::toString)
                .collect(Collectors.joining(", " + System.lineSeparator()));
    }

    @ShellMethod(value = "Get receipt by Id", key = "rid")
    public String findById(String id) {
        return converter.toString(service.findById(id));
    }

    @ShellMethod(value = "Insert new receipt", key = "rins")
    public String insertNew(String name, String caloriesId, String  dietId,
                            String mealtimeId) {
        var calories = caloriesTypeService.findById(caloriesId);
        var diet = dietTypeService.findById(dietId);
        var mealtime = mealtimeService.findById(mealtimeId);

        return converter.toString(service.create(new Receipt(null, name,
                calories, diet, mealtime, Collections.emptyList())));
    }

    @ShellMethod(value = "Update receipt", key = "rupd")
    public String update(String id, String name, String caloriesId,
                         String  dietId, String mealtimeId) {
        var calories = caloriesTypeService.findById(caloriesId);
        var diet = dietTypeService.findById(dietId);
        var mealtime = mealtimeService.findById(mealtimeId);
        var recipt = service.findById(id);
        return converter.toString(service.update(new Receipt(null, name,
                calories, diet, mealtime, recipt.getReceiptPositionsList())));
    }

    @ShellMethod(value = "Add position to receipt", key = "rposins")
    public String addPosition(String id, String servingId, String quantity) {
        try {
            var serving = servingService.findById(servingId);
            var receipt = service.findById(id);
            receipt.getReceiptPositionsList().add(new ReceiptPosition(serving, Long.parseLong(quantity)));
            return converter.toString(receipt);
        } catch (NumberFormatException ex) {
            return "Введите количество порций в виде целого числа!";
        }
    }

    @ShellMethod(value = "Delete receipt", key = "rdel")
    public void delete(String id) {
        mealtimeService.delete(id);
    }
}
