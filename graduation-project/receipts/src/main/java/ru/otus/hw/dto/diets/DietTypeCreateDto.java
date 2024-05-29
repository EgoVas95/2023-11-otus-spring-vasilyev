package ru.otus.hw.dto.diets;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DietTypeCreateDto {
    private Long id;

    @NotBlank(message = "Название типа диеты не может быть пустым!")
    @Size(min = 1, max = 255, message = "Длина типа диеты " +
            "должна быть в пределах от 1 до 255 символов!")
    private String name;
}
