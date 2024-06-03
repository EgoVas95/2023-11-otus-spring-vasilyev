package ru.otus.hw.models;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ReceiptPosition {
    @Valid
    private Serving serving;

    @NotNull(message = "Количество порций не может быть пустым!")
    @Positive(message = "Количество порций должно быть больше 0!")
    private Long quantity;
}
