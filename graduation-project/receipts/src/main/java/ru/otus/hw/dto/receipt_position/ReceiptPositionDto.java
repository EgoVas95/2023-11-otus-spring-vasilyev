package ru.otus.hw.dto.receipt_position;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.hw.dto.receipt.ReceiptDto;
import ru.otus.hw.dto.serving.ServingDto;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ReceiptPositionDto {
    private Long id;

    private ReceiptDto receiptDto;

    private ServingDto servingDto;

    private BigDecimal quantity;
}
