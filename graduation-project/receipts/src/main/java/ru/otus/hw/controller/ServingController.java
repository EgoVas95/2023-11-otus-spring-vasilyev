package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.models.Serving;
import ru.otus.hw.services.serving.ServingServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ServingController {
    private final ServingServiceImpl service;

    @GetMapping("/api/servings")
    public List<Serving> findAll() {
        return service.findAll();
    }

    @GetMapping("/api/servings/{serving_id}")
    public Serving findById(@PathVariable("serving_id") String id) {
        return service.findById(id);
    }

    @PostMapping("/api/servings")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Serving create(@Valid @RequestBody Serving serving) {
        return service.create(serving);
    }

    @PatchMapping("/api/servings/{serving_id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Serving update(@PathVariable("serving_id") String id,
            @Valid @RequestBody Serving serving) {
        serving.setId(id);
        return service.update(serving);
    }

    @DeleteMapping("/api/servings/{serving_id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("serving_id") String id) {
        service.delete(id);
    }
}
