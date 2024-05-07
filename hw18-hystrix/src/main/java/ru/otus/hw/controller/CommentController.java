package ru.otus.hw.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.CommentCreateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.CommentUpdateDto;
import ru.otus.hw.services.CommentServiceImpl;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentServiceImpl commentService;

    @CircuitBreaker(name = "commentBreaker", fallbackMethod = "unknownCommentListFallback")
    @GetMapping("/api/books/{id}/comments")
    public List<CommentDto> getCommentsForBook(@PathVariable("id") Long id) {
        return commentService.findAllForBook(id);
    }

    @CircuitBreaker(name = "commentBreaker", fallbackMethod = "unknownCommentServiceFallback")
    @GetMapping("/api/comments/{comment_id}")
    public CommentDto getCommentById(@PathVariable("comment_id") Long commentId) {
        return commentService.findById(commentId);
    }

    @CircuitBreaker(name = "commentBreaker", fallbackMethod = "createUpdateFallback")
    @PostMapping("/api/comments")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CommentDto addCommentForBook(@Valid @RequestBody CommentCreateDto dto) {
        return commentService.create(dto);
    }

    @CircuitBreaker(name = "commentBreaker", fallbackMethod = "createUpdateFallback")
    @PatchMapping("/api/comments/{comment_id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public CommentDto updateComment(@PathVariable("comment_id") Long commentId,
            @Valid @RequestBody CommentUpdateDto dto) {
        dto.setId(commentId);
        return commentService.update(dto);
    }

    @CircuitBreaker(name = "commentBreaker", fallbackMethod = "deleteFallback")
    @DeleteMapping("/api/comments/{comment_id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("comment_id") Long commentId) {
        commentService.deleteById(commentId);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public CommentDto unknownCommentServiceFallback(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new CommentDto(null, "NaN", null);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public List<CommentDto> unknownCommentListFallback(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ArrayList<>();
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String createUpdateFallback(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ex.getMessage();
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void deleteFallback(Exception ex) {
        log.error(ex.getMessage(), ex);
    }
}
