package ru.otus.hw.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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

    @NotBlank(message = "Наименование порции не может быть пустым!")
    private String name;

    @Valid
    private Food food;

    @NotNull(message = "Количество белка не должно быть пустым!")
    @PositiveOrZero(message = "Количество килокалорий не должно быть меньше 0!")
    private Long calories;
}
