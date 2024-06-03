package ru.otus.hw.models;

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
@Document(collection = "calories-type")
public class CaloriesType {
    @Id
    private String id;

    @NotNull(message = "Количество ккал не может быть пустым!")
    @PositiveOrZero(message = "Количество ккал не может быть отрицательным!")
    private Long calories;
}
