package ru.otus.hw.services.match;

import ru.otus.hw.dto.match.MatchMealtimeAndReceiptCreateDto;
import ru.otus.hw.dto.match.MatchMealtimeAndReceiptDto;
import ru.otus.hw.dto.match.MatchMealtimeAndReceiptUpdateDto;

import java.util.List;

public interface MatchMealtimeAndReceiptService {
    List<MatchMealtimeAndReceiptDto> findAll();

    MatchMealtimeAndReceiptDto findById(Long id);

    List<MatchMealtimeAndReceiptDto> findByReceiptId(Long id);

    List<MatchMealtimeAndReceiptDto> findByMealtimeId(Long id);

    MatchMealtimeAndReceiptDto create(MatchMealtimeAndReceiptCreateDto dto);

    MatchMealtimeAndReceiptDto update(MatchMealtimeAndReceiptUpdateDto dto);

    void delete(Long id);
}
