package ru.otus.hw.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.match.MatchMealtimeAndReceiptCreateDto;
import ru.otus.hw.dto.match.MatchMealtimeAndReceiptDto;
import ru.otus.hw.dto.match.MatchMealtimeAndReceiptUpdateDto;
import ru.otus.hw.models.MatchMealtimeAndReceipt;
import ru.otus.hw.models.MealtimeType;
import ru.otus.hw.models.Receipt;

@Component
@AllArgsConstructor
public class MatchMealtimeAndReceiptMapper {
    private final ReceiptMapper receiptMapper;

    private final MealtimeTypeMapper mealtimeTypeMapper;

    public MatchMealtimeAndReceipt toModel(MatchMealtimeAndReceiptDto dto) {
        return new MatchMealtimeAndReceipt(dto.getId(), receiptMapper.toModel(dto.getReceiptDto()),
                mealtimeTypeMapper.toModel(dto.getMealtimeTypeDto()));
    }

    public MatchMealtimeAndReceipt toModel(MatchMealtimeAndReceiptCreateDto dto, Receipt receipt,
                                           MealtimeType mealtimeType) {
        return new MatchMealtimeAndReceipt(null, receipt, mealtimeType);
    }

    public MatchMealtimeAndReceipt toModel(MatchMealtimeAndReceiptUpdateDto dto, Receipt receipt,
                                           MealtimeType mealtimeType) {
        return new MatchMealtimeAndReceipt(dto.getId(), receipt, mealtimeType);
    }

    public MatchMealtimeAndReceiptDto toDto(MatchMealtimeAndReceipt match) {
        return new MatchMealtimeAndReceiptDto(match.getId(), receiptMapper.toDto(match.getReceipt()),
                mealtimeTypeMapper.toDto(match.getMealtimeType()));
    }
}
