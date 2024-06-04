package ru.otus.hw.mealconfigurator.model.receipt_models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Document(collection = "servings")
public class Serving {
    @Id
    private String id;

    private String name;

    private Food food;

    private Long calories;
}
