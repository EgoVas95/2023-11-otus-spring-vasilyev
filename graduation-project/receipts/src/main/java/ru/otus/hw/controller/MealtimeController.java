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
import ru.otus.hw.models.Mealtime;
import ru.otus.hw.services.mealtime.MealtimeServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MealtimeController {
    private final MealtimeServiceImpl service;

    @GetMapping("/api/mealtimes")
    public List<Mealtime> findAll() {
        return service.findAll();
    }

    @GetMapping("/api/mealtimes/{mealtime_id}")
    public Mealtime findById(@PathVariable("mealtime_id") String id) {
        return service.findById(id);
    }

    @PostMapping("/api/mealtimes")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mealtime create(@Valid @RequestBody Mealtime mealtime) {
        return service.create(mealtime);
    }

    @PatchMapping("/api/mealtimes/{mealtimes_id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Mealtime update(@PathVariable("mealtimes_id") String id,
            @Valid @RequestBody Mealtime mealtime) {
        mealtime.setId(id);
        return service.update(mealtime);
    }

    @DeleteMapping("/api/mealtimes/{mealtimes_id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("mealtimes_id") String id) {
        service.delete(id);
    }
}
