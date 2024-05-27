package ru.otus.hw.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.receipt.ReceiptCreateDto;
import ru.otus.hw.dto.receipt.ReceiptDto;
import ru.otus.hw.dto.receipt.ReceiptUpdateDto;
import ru.otus.hw.models.Food;
import ru.otus.hw.models.Receipt;

@Component
@AllArgsConstructor
public class ReceiptMapper {
    private final FoodMapper foodMapper;

    public Receipt toModel(ReceiptDto receiptDto) {
        return new Receipt(receiptDto.getId(), foodMapper.toModel(receiptDto.getFoodDto()),
                receiptDto.getInstruction());
    }

    public Receipt toModel(ReceiptCreateDto receiptDto, Food food) {
        return new Receipt(null, food, receiptDto.getInstruction());
    }

    public Receipt toModel(ReceiptUpdateDto receiptDto, Food food) {
        return new Receipt(receiptDto.getId(), food, receiptDto.getInstruction());
    }

    public ReceiptDto toDto(Receipt receipt) {
        return new ReceiptDto(receipt.getId(), foodMapper.toDto(receipt.getFood()),
                receipt.getInstruction());
    }
}
