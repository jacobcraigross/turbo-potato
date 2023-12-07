package com.coldcoffee.demo.service;
import com.coldcoffee.demo.model.BookEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public interface BookService {
    BookEntity createBook(String isbn, BookEntity bookEntity);

    List<BookEntity> getAllBooks();

    Optional<BookEntity> getBookByIsbn(String isbn);

}
