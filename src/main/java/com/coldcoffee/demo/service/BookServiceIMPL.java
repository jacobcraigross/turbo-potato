package com.coldcoffee.demo.service;
import com.coldcoffee.demo.model.BookEntity;
import com.coldcoffee.demo.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookServiceIMPL implements BookService {

    private final BookRepository bookRepository;

    public BookServiceIMPL(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Override
    public BookEntity createBook(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);
        return bookRepository.save(bookEntity);
    }
}
