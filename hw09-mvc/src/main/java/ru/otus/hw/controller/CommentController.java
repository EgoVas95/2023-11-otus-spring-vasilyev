package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.services.BookServiceImpl;
import ru.otus.hw.services.CommentServiceImpl;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentServiceImpl commentService;

    private final BookServiceImpl bookService;

    @GetMapping("/book_comments")
    public String getCommentsForBook(@RequestParam("id") long id, Model model) {
        Book book = bookService.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Not found book for id = %d".formatted(id)));
        List<Comment> comments = commentService.findAllForBook(id);

        model.addAttribute("book", BookDto.fromDomainObject(book));
        model.addAttribute("comments", comments);

        return "comments/book_comments";
    }
}
