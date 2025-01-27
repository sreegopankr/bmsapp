package com.books.bms.Service;

import com.books.bms.Entity.Book;
import com.books.bms.Repo.BookRepo;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class BookService {
    @Autowired
    private BookRepo bookRepository;
    private int idCount;

    @PostConstruct
    public void init() {
        // Fetch the last book ID from the database
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            idCount = 0; // If no books are present, start from 0
        } else {
            // Find the highest ID and extract the number part
            String lastBookId = books.stream()
                    .map(Book::getId)
                    .max(String::compareTo)
                    .orElse("B-000");
            // Extract the numeric part and set idCount
            idCount = Integer.parseInt(lastBookId.substring(2));
        }
    }

    public Book addBook(@Valid Book book) {
        // Generate unique book ID based on count
        String bookId = "B-" + String.format("%03d", ++idCount);
        book.setId(bookId);
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();

    }

    public Optional<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }

    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }

    public Book updateBook(Book prevBook) {
        return bookRepository.save(prevBook);
    }
}
