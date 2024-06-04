package ru.otus.hw.mealsbot.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Serving {
    private String id;

    private String name;

    private Food food;

    private Long calories;
}
