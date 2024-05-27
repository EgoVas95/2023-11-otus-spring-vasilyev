package ru.otus.hw.dto.mealtime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MealtimeTypeUpdateDto {
    @NotNull(message = "ID типа приёма пищи не может быть пустым!")
    private Long id;

    @NotBlank(message = "Название типа приёма пищи не может быть пустым!")
    @Size(min = 1, max = 255, message = "Длина типа приёма пищи " +
            "должна быть в пределах от 1 до 255 символов!")
    private String name;
}
