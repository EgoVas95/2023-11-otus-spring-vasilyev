package ru.otus.hw.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.receipt_position.ReceiptPositionCreateDto;
import ru.otus.hw.dto.receipt_position.ReceiptPositionDto;
import ru.otus.hw.dto.receipt_position.ReceiptPositionUpdateDto;
import ru.otus.hw.models.ReceiptPosition;
import ru.otus.hw.models.Receipt;
import ru.otus.hw.models.Serving;

@Component
@AllArgsConstructor
public class ReceiptPositionMapper {
    private final ReceiptMapper receiptMapper;

    private final ServingMapper servingMapper;

    public ReceiptPosition toModel(ReceiptPositionDto dto) {
        return new ReceiptPosition(dto.getId(), receiptMapper.toModel(dto.getReceiptDto()),
                servingMapper.toModel(dto.getServingDto()), dto.getQuantity());
    }

    public ReceiptPosition toModel(ReceiptPositionCreateDto dto, Receipt receipt, Serving serving) {
        return new ReceiptPosition(null, receipt, serving, dto.getQuantity());
    }

    public ReceiptPosition toModel(ReceiptPositionUpdateDto dto, Receipt receipt, Serving serving) {
        return new ReceiptPosition(dto.getId(), receipt, serving, dto.getQuantity());
    }

    public ReceiptPositionDto toDto(ReceiptPosition receiptPosition) {
        return new ReceiptPositionDto(receiptPosition.getId(), receiptMapper.toDto(receiptPosition.getReceipt()),
                servingMapper.toDto(receiptPosition.getServing()), receiptPosition.getQuantity());
    }
}
