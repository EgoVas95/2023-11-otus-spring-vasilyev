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
import ru.otus.hw.dto.mealtime.MealtimeTypeCreateDto;
import ru.otus.hw.dto.mealtime.MealtimeTypeDto;
import ru.otus.hw.dto.mealtime.MealtimeTypeUpdateDto;
import ru.otus.hw.services.mealtime.MealtimeTypeServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MealtimeController {
    private final MealtimeTypeServiceImpl service;

    @GetMapping("/api/mealtimes")
    public List<MealtimeTypeDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/api/mealtimes/{mealtime_id}")
    public MealtimeTypeDto findById(@PathVariable("mealtime_id") Long id) {
        return service.findById(id);
    }

    @GetMapping("/api/mealtimes/name/{mealtime_name}")
    public MealtimeTypeDto findByName(@PathVariable("mealtime_name") String name) {
        return service.findByName(name);
    }

    @PostMapping("/api/mealtimes")
    @ResponseStatus(value = HttpStatus.CREATED)
    public MealtimeTypeDto create(@Valid @RequestBody MealtimeTypeCreateDto dto) {
        return service.create(dto);
    }

    @PatchMapping("/api/mealtimes/{mealtimes_id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public MealtimeTypeDto update(@PathVariable("mealtimes_id") Long id,
            @Valid @RequestBody MealtimeTypeUpdateDto dto) {
        dto.setId(id);
        return service.update(dto);
    }

    @DeleteMapping("/api/mealtimes/{mealtimes_id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("mealtimes_id") Long id) {
        service.delete(id);
    }
}
