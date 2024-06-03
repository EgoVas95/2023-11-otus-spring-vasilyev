package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converter.MealtimeConverter;
import ru.otus.hw.models.Mealtime;
import ru.otus.hw.services.mealtime.MealtimeService;

import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class MealCommand {
    private final MealtimeService service;

    private final MealtimeConverter converter;

    @ShellMethod(value = "Get all mealtimes", key = "mta")
    public String findAll() {
        return service.findAll().stream()
                .map(converter::toString)
                .collect(Collectors.joining(", " + System.lineSeparator()));
    }

    @ShellMethod(value = "Get mealtime by Id", key = "mid")
    public String findById(String id) {
        return converter.toString(service.findById(id));
    }

    @ShellMethod(value = "Insert new mealtime", key = "mtins")
    public String insertNew(String val) {
        return converter.toString(service.create(new Mealtime(null, val)));
    }

    @ShellMethod(value = "Update mealtime", key = "mtupd")
    public String update(String id, String val) {
        return converter.toString(service.update(new Mealtime(id, val)));
    }

    @ShellMethod(value = "Delete mealtime", key = "mtdel")
    public void delete(String id) {
        service.delete(id);
    }
}
