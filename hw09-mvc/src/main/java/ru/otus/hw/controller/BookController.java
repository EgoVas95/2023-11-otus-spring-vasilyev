package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;

import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.AuthorServiceImpl;
import ru.otus.hw.services.BookServiceImpl;
import ru.otus.hw.services.GenreServiceImpl;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookServiceImpl bookService;

    private final AuthorServiceImpl authorService;

    private final GenreServiceImpl genreService;

    @GetMapping("/")
    public String allBooksList(Model model) {
        List<BookDto> books = bookService.findAll();
        model.addAttribute("books", books);
        return "books/all_books";
    }

    @GetMapping("/edit_book")
    public String editBook(@RequestParam(value = "id", required = false) Long id, Model model) {
        if (id == null) {
            BookCreateDto book = new BookCreateDto(null, null,
                    new AuthorDto(null, null),
                    new GenreDto(null, null));
            model.addAttribute("book", book);
        } else {
            BookDto book = bookService.findById(id);
            model.addAttribute("book", BookUpdateDto.fromBookDto(book));
        }
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "books/edit_book";
    }

    @PostMapping("/update_book")
    public String updateBook(@Valid @ModelAttribute("book") BookUpdateDto book,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("genres", genreService.findAll());
            return "redirect:/edit_book";
        }

        bookService.update(book);
        return "redirect:/";
    }


    @PostMapping("/create_book")
    public String createBook(@Valid @ModelAttribute("book") BookCreateDto book,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("genres", genreService.findAll());
            return "books/edit_book";
        }

        bookService.create(book);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteBook(@RequestParam("id") long id, Model model) {
        bookService.deleteById(id);
        return "redirect:/";
    }
}
