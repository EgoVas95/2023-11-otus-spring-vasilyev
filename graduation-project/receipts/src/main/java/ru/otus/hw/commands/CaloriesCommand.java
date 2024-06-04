package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converter.CaloriesTypeConverter;
import ru.otus.hw.models.CaloriesType;
import ru.otus.hw.services.calories.CaloriesTypeService;

import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class CaloriesCommand {
    private final CaloriesTypeService service;

    private final CaloriesTypeConverter converter;

    @ShellMethod(value = "Get all calories types", key = "cta")
    public String findAll() {
        return service.findAll().stream()
                .map(converter::toString)
                .collect(Collectors.joining(", " + System.lineSeparator()));
    }

    @ShellMethod(value = "Get calories type by Id", key = "ctid")
    public String findById(String id) {
        return converter.toString(service.findById(id));
    }

    @ShellMethod(value = "Insert new calories type", key = "ctins")
    public String insertNew(String calories) {
        try {
            Long val = Long.parseLong(calories);
            return converter.toString(service.create(new CaloriesType(null, val)));
        } catch (NumberFormatException ex) {
            return "Укажите количество калорий в виде целого числа!";
        }
    }

    @ShellMethod(value = "Update calories type", key = "ctupd")
    public String update(String id, String calories) {
        try {
            Long val = Long.parseLong(calories);
            return converter.toString(service.update(new CaloriesType(id, val)));
        } catch (NumberFormatException ex) {
            return "Укажите количество калорий в виде целого числа!";
        }
    }

    @ShellMethod(value = "Delete caloriest type", key = "ctdel")
    public void delete(String id) {
        service.delete(id);
    }
}

