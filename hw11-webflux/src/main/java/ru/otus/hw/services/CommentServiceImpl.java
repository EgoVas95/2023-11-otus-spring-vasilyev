package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.CommentCreateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.CommentUpdateDto;
import ru.otus.hw.mappers.CommentMapper;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    private final CommentMapper commentMapper;

    @Transactional(readOnly = true)
    @Override
    public Mono<CommentDto> findById(Long id) {
        return commentRepository.findById(id)
                .map(commentMapper::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public Flux<CommentDto> findAllForBook(Long bookId) {
        return commentRepository.findAllByBookId(bookId)
                .map(commentMapper::toDto);
    }

    @Transactional
    @Override
    public Mono<CommentDto> create(CommentCreateDto dto) {
        return bookRepository.findById(dto.getBookId())
                .flatMap(result -> {
                    var comment = commentMapper.toModel(dto, result);
                    return commentRepository.save(comment)
                            .map(commentMapper::toDto);
                });
    }

    @Transactional
    @Override
    public Mono<CommentDto> update(CommentUpdateDto dto) {
        return bookRepository.findById(dto.getBookId())
                .flatMap(result -> {
                    var comment = commentMapper.toModel(dto, result);
                    return commentRepository.save(comment)
                            .map(commentMapper::toDto);
                });
    }

    @Transactional
    @Override
    public Mono<Void> deleteById(Long id) {
        return commentRepository.deleteById(id);
    }
}
