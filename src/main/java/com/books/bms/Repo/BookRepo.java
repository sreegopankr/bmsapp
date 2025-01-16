package com.books.bms.Repo;

import com.books.bms.Entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo extends MongoRepository<Book,String> {
    Book findByIsbn(String isbn);
}
