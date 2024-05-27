package ru.otus.hw.dto.match;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MatchMealtimeAndReceiptUpdateDto {
    @NotNull(message = "ID связи рецепта и типа приёма пищи не может быть пустым!")
    private Long id;

    @NotNull(message = "ID рецепта не может быть пустым!")
    private Long receiptId;

    @NotNull(message = "ID типа приёма пищи не может быть пустым!")
    private Long meailtimeTypeId;
}
