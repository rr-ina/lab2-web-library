package com.example.library.services;

import com.example.library.models.Book;
import com.example.library.models.Hall;
import com.example.library.repository.BookRepository;
import com.example.library.repository.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final HallRepository hallRepository;

    public BookService(BookRepository bookRepository, HallRepository hallRepository) {
        this.bookRepository = bookRepository;
        this.hallRepository = hallRepository;
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> findBookById(Integer id) {
        return bookRepository.findById(id);
    }

    @Transactional
    public Book saveBook(Book book) {
        // Валідація обов’язкових полів
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Назва книги є обов’язковою");
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Автор є обов’язковим");
        }
        if (book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN є обов’язковим");
        }

        // Перевірка, чи існує зала (якщо вказана)
        if (book.getHall() != null && book.getHall().getId() != null) {
            if (!hallRepository.existsById(book.getHall().getId())) {
                throw new IllegalArgumentException("Вказана зала не існує");
            }
        } else {
            book.setHall(null); // Якщо зала не вибрана, встановлюємо null
        }

        return bookRepository.save(book);
    }

    @Transactional
    public void deleteBookById(Integer id) {
        if (!bookRepository.existsById(id)) {
            throw new IllegalArgumentException("Книга з ID " + id + " не знайдена");
        }
        bookRepository.deleteById(id);
    }

    public List<Hall> findAllHalls() {
        return hallRepository.findAll();
    }
}