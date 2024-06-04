package ru.otus.hw.mealconfigurator.model.receipt_models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Food {
    @Id
    private String id;

    private String name;
}
