package ru.otus.hw.services.match;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.match.MatchMealtimeAndReceiptCreateDto;
import ru.otus.hw.dto.match.MatchMealtimeAndReceiptDto;
import ru.otus.hw.dto.match.MatchMealtimeAndReceiptUpdateDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.MatchMealtimeAndReceiptMapper;
import ru.otus.hw.repositories.MatchMealtimeAndReceiptRepository;
import ru.otus.hw.repositories.MealtimeTypeRepository;
import ru.otus.hw.repositories.ReceiptsRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MatchMealtimeAndReceiptServiceImpl implements MatchMealtimeAndReceiptService {

    private final MatchMealtimeAndReceiptMapper matchMapper;


    private final MatchMealtimeAndReceiptRepository matchRepository;

    private final ReceiptsRepository receiptsRepository;

    private final MealtimeTypeRepository mealtimeTypeRepository;

    @Override
    public List<MatchMealtimeAndReceiptDto> findAll() {
        return matchRepository.findAll()
                .stream().map(matchMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MatchMealtimeAndReceiptDto findById(Long id) {
        return matchMapper.toDto(matchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Не найдено соответствие между типом завтрака и рецепта с id = %d!"
                                .formatted(id))));
    }

    @Override
    public List<MatchMealtimeAndReceiptDto> findByReceiptId(Long id) {
        return matchRepository.findAllByReceiptId(id)
                .stream().map(matchMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MatchMealtimeAndReceiptDto> findByMealtimeId(Long id) {
        return matchRepository.findAllByMealtimeTypeId(id)
                .stream().map(matchMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MatchMealtimeAndReceiptDto create(MatchMealtimeAndReceiptCreateDto dto) {
        var receiptId = dto.getReceiptId();
        var receipt = receiptsRepository.findById(receiptId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Рецепт с id = %d не найден!".formatted(receiptId)));

        var mealtimeId = dto.getMeailtimeTypeId();
        var mealtimeType = mealtimeTypeRepository.findById(mealtimeId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Тип приёма пищи с id = %d не найден!"
                                .formatted(mealtimeId)));

        return matchMapper.toDto(matchRepository.save(
                matchMapper.toModel(dto, receipt, mealtimeType)));
    }

    @Override
    @Transactional
    public MatchMealtimeAndReceiptDto update(MatchMealtimeAndReceiptUpdateDto dto) {
        var receiptId = dto.getReceiptId();
        var receipt = receiptsRepository.findById(receiptId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Рецепт с id = %d не найден!".formatted(receiptId)));

        var mealtimeId = dto.getMeailtimeTypeId();
        var mealtimeType = mealtimeTypeRepository.findById(mealtimeId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Тип приёма пищи с id = %d не найден!"
                                .formatted(mealtimeId)));

        return matchMapper.toDto(matchRepository.save(
                matchMapper.toModel(dto, receipt, mealtimeType)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        matchRepository.deleteById(id);
    }
}
