package ru.otus.hw.services.receipt_position;

import ru.otus.hw.dto.receipt_position.ReceiptPositionCreateDto;
import ru.otus.hw.dto.receipt_position.ReceiptPositionDto;
import ru.otus.hw.dto.receipt_position.ReceiptPositionUpdateDto;

import java.util.List;

public interface ReceiptPositionService {
    List<ReceiptPositionDto> findAll();

    ReceiptPositionDto findById(Long id);

    List<ReceiptPositionDto> findByReceiptId(Long id);

    ReceiptPositionDto create(ReceiptPositionCreateDto dto);

    ReceiptPositionDto update(ReceiptPositionUpdateDto dto);

    void delete(Long id);
}
