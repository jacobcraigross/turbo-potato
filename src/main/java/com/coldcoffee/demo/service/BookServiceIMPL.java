package com.coldcoffee.demo.service;
import com.coldcoffee.demo.model.BookEntity;
import com.coldcoffee.demo.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class BookServiceIMPL implements BookService {


    // ---------- CONSTRUCTOR INJECTION --------------------------------------------------------------------------------
    private final BookRepository bookRepository;
    public BookServiceIMPL(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    // ---------- CREATE BOOK ------------------------------------------------------------------------------------------
    @Override
    public BookEntity createBook(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);
        return bookRepository.save(bookEntity);
    }


    // ---------- GET ALL BOOKS ----------------------------------------------------------------------------------------
    @Override
    public List<BookEntity> getAllBooks() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }


}
