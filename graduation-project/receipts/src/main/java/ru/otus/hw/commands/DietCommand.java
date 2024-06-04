package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converter.DietConverter;
import ru.otus.hw.models.DietType;
import ru.otus.hw.services.diet.DietTypeService;

import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class DietCommand {
    private final DietTypeService service;

    private final DietConverter converter;

    @ShellMethod(value = "Get all diet types", key = "dta")
    public String findAll() {
        return service.findAll().stream()
                .map(converter::toString)
                .collect(Collectors.joining(", " + System.lineSeparator()));
    }

    @ShellMethod(value = "Get calories type by Id", key = "dtid")
    public String findById(String id) {
        return converter.toString(service.findById(id));
    }

    @ShellMethod(value = "Insert new calories type", key = "dtins")
    public String insertNew(String val) {
        return converter.toString(service.create(new DietType(null, val)));
    }

    @ShellMethod(value = "Update calories type", key = "dtupd")
    public String update(String id, String val) {
        return converter.toString(service.update(new DietType(id, val)));
    }

    @ShellMethod(value = "Delete diet type", key = "dtdel")
    public void delete(String id) {
        service.delete(id);
    }
}
