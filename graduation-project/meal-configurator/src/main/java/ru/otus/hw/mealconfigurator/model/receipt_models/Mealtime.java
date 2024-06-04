package ru.otus.hw.mealconfigurator.model.receipt_models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Mealtime {

    private String id;

    private String name;
}
