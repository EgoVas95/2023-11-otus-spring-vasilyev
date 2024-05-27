package ru.otus.hw.dto.food;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FoodCreateDto {
    private Long id;

    @NotBlank(message = "Название продукта не может быть пустым!")
    @Size(min = 1, max = 255, message = "Длина названия продукта " +
            "должна быть в пределах от 1 до 255 символов!")
    private String name;
}
