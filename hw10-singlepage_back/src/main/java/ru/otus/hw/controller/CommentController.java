package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.CommentCreateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.CommentUpdateDto;
import ru.otus.hw.services.CommentServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentServiceImpl commentService;

    @GetMapping("/api/books/{id}/comments")
    public List<CommentDto> getCommentsForBook(@PathVariable("id") Long id) {
        return commentService.findAllForBook(id);
    }

    @GetMapping("/api/comments/{comment_id}")
    public CommentDto getCommentById(@PathVariable("comment_id") Long commentId) {
        return commentService.findById(commentId);
    }

    @PostMapping("/api/comments")
    public CommentDto addCommentForBook(@Valid @RequestBody CommentCreateDto dto) {
        return commentService.create(dto);
    }

    @PatchMapping("/api/comments/{comment_id}")
    public CommentDto updateComment(@PathVariable("comment_id") Long commentId,
            @Valid @RequestBody CommentUpdateDto dto) {
        return commentService.update(dto);
    }

    @DeleteMapping("/api/comments/{comment_id}")
    public void deleteComment(@PathVariable("comment_id") Long commentId) {
        commentService.deleteById(commentId);
    }
}
