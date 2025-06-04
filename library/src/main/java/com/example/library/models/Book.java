package com.example.library.models;
import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 100)
    private String author;

    @Column(nullable = false, length = 13, unique = true)
    private String isbn;

    @Column(name = "publication_year")
    private Integer publicationYear;

    // Зв’язок багато-до-одного: багато книг — в одній залі
    @ManyToOne
    @JoinColumn(name = "hall_id", foreignKey = @ForeignKey(name = "fk_book_hall"))
    private Hall hall;

    // Конструктори
    public Book() {}

    public Book(String title, String author, String isbn, Integer publicationYear, Hall hall) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.hall = hall;
    }

    // Геттери і сеттери
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Integer getPublicationYear() { return publicationYear; }
    public void setPublicationYear(Integer publicationYear) { this.publicationYear = publicationYear; }

    public Hall getHall() { return hall; }
    public void setHall(Hall hall) { this.hall = hall; }
}

