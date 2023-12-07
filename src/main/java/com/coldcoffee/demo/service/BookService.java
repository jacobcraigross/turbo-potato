package com.coldcoffee.demo.service;
import com.coldcoffee.demo.model.BookEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface BookService {
    BookEntity createBook(String isbn, BookEntity bookEntity);

    List<BookEntity> getAllBooks();
}
