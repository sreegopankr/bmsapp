package com.books.bms.Controller;

import com.books.bms.Entity.Book;
import com.books.bms.Service.BookApiService;
import com.books.bms.Service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookApiService bookApiService;

    @PostMapping
    public ResponseEntity<?> addBook(@Valid @RequestBody Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        Book savedBook = bookService.addBook(book);
        return ResponseEntity.ok(savedBook);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable String id) {
        Optional<Book> book = bookService.getBookById(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@Valid @RequestBody Book updatedBook, BindingResult bindingResult,@PathVariable String id){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        Optional<Book> prevBookOpt = bookService.getBookById(id);
        if(prevBookOpt.isPresent()){
            Book prevBook = prevBookOpt.get();
            prevBook.setTitle(updatedBook.getTitle());
            prevBook.setAuthor(updatedBook.getAuthor());
            prevBook.setGenre(updatedBook.getGenre());
            prevBook.setIsbn(updatedBook.getIsbn());
            prevBook.setPublicationDate(updatedBook.getPublicationDate());
            prevBook.setRating(updatedBook.getRating());

            Book savedBook = bookService.updateBook(prevBook);

            return ResponseEntity.ok(savedBook);
        }else{
            return ResponseEntity.status(404).body("Book not found with ID :" + id);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint to fetch book details from Google Books API
    @GetMapping("/details/{isbn}")
    public String getBookDetails(@PathVariable String isbn) {
        return bookApiService.getBookDetailsFromGoogle(isbn);
    }
}
