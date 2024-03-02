package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.GenreServiceImpl;


@RestController
@RequiredArgsConstructor
public class GenreController {
    private final GenreServiceImpl genreService;

    @GetMapping("/api/genres")
    public Flux<GenreDto> allGenresList() {
        return genreService.findAll();
    }
}
