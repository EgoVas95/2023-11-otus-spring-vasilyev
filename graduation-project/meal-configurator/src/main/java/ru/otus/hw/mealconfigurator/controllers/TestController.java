package ru.otus.hw.mealconfigurator.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/api/plans")
    public String home() {
        return "Hello madafaka!";
    }
}
