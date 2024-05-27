package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.match.MatchMealtimeAndReceiptCreateDto;
import ru.otus.hw.dto.match.MatchMealtimeAndReceiptDto;
import ru.otus.hw.dto.match.MatchMealtimeAndReceiptUpdateDto;
import ru.otus.hw.services.match.MatchMealtimeAndReceiptServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MatchController {
    private final MatchMealtimeAndReceiptServiceImpl service;

    @GetMapping("/api/matches")
    public List<MatchMealtimeAndReceiptDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/api/matches/{match_id}")
    public MatchMealtimeAndReceiptDto findById(@PathVariable("match_id") Long id) {
        return service.findById(id);
    }

    @GetMapping("/api/receipts/matches/{receipt_id}")
    public List<MatchMealtimeAndReceiptDto> findByReceiptId(@PathVariable("receipt_id") Long id) {
        return service.findByReceiptId(id);
    }

    @GetMapping("/api/mealtimes/matches/{mealtime_id}")
    public List<MatchMealtimeAndReceiptDto> findByMealtimeId(@PathVariable("mealtime_id") Long id) {
        return service.findByMealtimeId(id);
    }

    @PostMapping("/api/matches")
    @ResponseStatus(value = HttpStatus.CREATED)
    public MatchMealtimeAndReceiptDto create(
            @Valid @RequestBody MatchMealtimeAndReceiptCreateDto dto) {
        return service.create(dto);
    }

    @PatchMapping("/api/matches/{match_id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public MatchMealtimeAndReceiptDto update(@PathVariable("match_id") Long matchId,
                                             @Valid @RequestBody MatchMealtimeAndReceiptUpdateDto dto) {
        dto.setId(matchId);
        return service.update(dto);
    }

    @DeleteMapping("/api/matches/{match_id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("match_id") Long id) {
        service.delete(id);
    }
}
