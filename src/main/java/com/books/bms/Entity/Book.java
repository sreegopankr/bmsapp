package com.books.bms.Entity;

import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection ="books")
public class Book {
    @Id
    private String id;

    @NotEmpty(message = "Title is required.")
    @Size(max = 100, message = "Title cannot be longer than 100 characters.")
    private String title;

    @NotEmpty(message = "Author is required.")
    @Size(max = 50, message = "Author name cannot be longer than 50 characters.")
    private String author;

    @NotNull(message = "Publication date is required.")
    private LocalDate publicationDate;

    @NotNull(message = "ISBN is required.")
    @Pattern(regexp = "\\d{13}", message = "ISBN must be a 13-digit number.")
    private String isbn;

    private String genre;

    @Min(1)
    @Max(5)
    private int rating;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
