package ru.otus.hw.models;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
@Document(collection = "diet-types")
public class DietType {
    @Id
    private String id;

    @NotBlank(message = "Название типа диеты не может быть пустым!")
    @Size(min = 1, max = 255, message = "Длина типа диеты " +
            "должна быть в пределах от 1 до 255 символов!")
    private String name;
}
