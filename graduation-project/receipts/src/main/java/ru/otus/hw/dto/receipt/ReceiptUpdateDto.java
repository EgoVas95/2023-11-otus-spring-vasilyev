package ru.otus.hw.dto.receipt;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReceiptUpdateDto {
    @NotNull(message = "ID рецепта не может быть пустым!")
    private Long id;

    @NotNull(message = "ID продукта не может быть пустым!")
    private Long foodId;

    @NotBlank(message = "Заполните способ приготовления продукта!")
    private String instruction;
}
