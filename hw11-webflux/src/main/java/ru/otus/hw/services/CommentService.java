package ru.otus.hw.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.CommentCreateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.CommentUpdateDto;

public interface CommentService {
    Mono<CommentDto> findById(Long id);

    Flux<CommentDto> findAllForBook(Long bookId);

    Mono<CommentDto> create(CommentCreateDto dto);

    Mono<CommentDto> update(CommentUpdateDto dto);

    Mono<Void> deleteById(Long id);
}
