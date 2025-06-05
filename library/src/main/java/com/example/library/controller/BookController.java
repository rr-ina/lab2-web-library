package com.example.library.controller;

import com.example.library.models.Book;
import com.example.library.services.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.findAllBooks());
        return "BookList";
    }

    @GetMapping("/form")
    public String showForm(@RequestParam(required = false) Integer id, Model model) {
        Book book;
        if (id != null) {
            book = bookService.findBookById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Книга з ID " + id + " не знайдена"));
        } else {
            book = new Book();
        }
        model.addAttribute("book", book);
        model.addAttribute("halls", bookService.findAllHalls());
        return "BookForm";
    }

    @PostMapping
    public String saveBook(@ModelAttribute Book book, Model model) {
        try {
            bookService.saveBook(book);
            return "redirect:/books";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Integer id, Model model) {
        try {
            bookService.deleteBookById(id);
            return "redirect:/books";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}