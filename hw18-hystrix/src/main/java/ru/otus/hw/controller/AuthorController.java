package ru.otus.hw.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.services.AuthorServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@CircuitBreaker(name = "authorBreaker", fallbackMethod = "unknownAuthorListFallback")
@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorServiceImpl authorService;

    @GetMapping("/api/authors")
    public List<AuthorDto> allAuthorsList() {
        return authorService.findAll();
    }

    public List<AuthorDto> unknownAuthorListFallback(Exception ex) {
        log.error(ex.getMessage());
        return new ArrayList<>();
    }
}
