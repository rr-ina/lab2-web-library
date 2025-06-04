package com.example.library.repository;

import com.example.library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    // Кастомні методи, якщо потрібні
    // List<Book> findByTitleContainingIgnoreCase(String title);
}