package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converter.FoodConverter;
import ru.otus.hw.models.Food;
import ru.otus.hw.services.food.FoodService;

import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class FoodCommand {
    private final FoodService service;

    private final FoodConverter converter;

    @ShellMethod(value = "Get all foods", key = "fa")
    public String findAll() {
        return service.findAll().stream()
                .map(converter::toString)
                .collect(Collectors.joining(", " + System.lineSeparator()));
    }

    @ShellMethod(value = "Get food by Id", key = "fid")
    public String findById(String id) {
        return converter.toString(service.findById(id));
    }

    @ShellMethod(value = "Insert new food", key = "fins")
    public String insertNew(String val) {
        return converter.toString(service.create(new Food(null, val)));
    }

    @ShellMethod(value = "Update food", key = "fupd")
    public String update(String id, String val) {
        return converter.toString(service.update(new Food(id, val)));
    }

    @ShellMethod(value = "Delete food type", key = "fdel")
    public void delete(String id) {
        service.delete(id);
    }
}
