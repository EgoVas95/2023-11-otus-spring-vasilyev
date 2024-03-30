package ru.otus.hw.services;

import org.springframework.security.access.prepost.PreAuthorize;
import ru.otus.hw.dto.CommentCreateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.CommentUpdateDto;

import java.util.List;

public interface CommentService {
    @PreAuthorize("hasRole('USER')")
    CommentDto findById(Long id);

    @PreAuthorize("hasRole('USER')")
    List<CommentDto> findAllForBook(Long bookId);

    @PreAuthorize("hasRole('ADMIN')")
    CommentDto create(CommentCreateDto dto);

    @PreAuthorize("hasRole('ADMIN')")
    CommentDto update(CommentUpdateDto dto);

    @PreAuthorize("hasRole('ADMIN')")
    void deleteById(Long id);
}
