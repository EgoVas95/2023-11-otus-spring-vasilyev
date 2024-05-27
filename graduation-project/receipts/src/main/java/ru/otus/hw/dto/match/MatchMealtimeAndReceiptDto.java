package ru.otus.hw.dto.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.hw.dto.mealtime.MealtimeTypeDto;
import ru.otus.hw.dto.receipt.ReceiptDto;

@Data
@AllArgsConstructor
public class MatchMealtimeAndReceiptDto {
    private Long id;

    private ReceiptDto receiptDto;

    private MealtimeTypeDto mealtimeTypeDto;
}
