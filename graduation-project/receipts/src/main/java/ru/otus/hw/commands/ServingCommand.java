package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converter.ServingConverter;
import ru.otus.hw.models.Serving;
import ru.otus.hw.services.food.FoodService;
import ru.otus.hw.services.serving.ServingService;

import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class ServingCommand {
    private final ServingService service;

    private final FoodService foodService;

    private final ServingConverter converter;

    @ShellMethod(value = "Get all servings", key = "sa")
    public String findAll() {
        return service.findAll().stream()
                .map(converter::toString)
                .collect(Collectors.joining(", " + System.lineSeparator()));
    }

    @ShellMethod(value = "Get serving by Id", key = "sid")
    public String findById(String id) {
        return converter.toString(service.findById(id));
    }

    @ShellMethod(value = "Insert new serving", key = "sins")
    public String insertNew(String val, String foodId, Long calories) {
        var food = foodService.findById(foodId);
        return converter.toString(service.create(new Serving(null, val,
                food, calories)));
    }

    @ShellMethod(value = "Update serving", key = "supd")
    public String update(String id, String val, String foodId, Long calories) {
        var food = foodService.findById(foodId);
        return converter.toString(service.update(new Serving(id, val,
                food, calories)));
    }

    @ShellMethod(value = "Delete serving", key = "sdel")
    public void delete(String id) {
        service.delete(id);
    }
}
