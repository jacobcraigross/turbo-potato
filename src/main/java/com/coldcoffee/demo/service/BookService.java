package com.coldcoffee.demo.service;
import com.coldcoffee.demo.model.BookEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public interface BookService {

    // ---------- CREATE OR UPDATE BOOK --------------------------------------------------------------------------------
    BookEntity createOrUpdateBook(String isbn, BookEntity bookEntity);

    // ---------- GET ALL BOOKS ----------------------------------------------------------------------------------------
    List<BookEntity> getAllBooks();

    // ---------- GET BOOK BY ISBN -------------------------------------------------------------------------------------
    Optional<BookEntity> getBookByIsbn(String isbn);

    // ---------- DOES BOOK EXIST --------------------------------------------------------------------------------------
    boolean doesBookExist(String isbn);

    // ---------- PARTIAL UPDATE BOOK BY ISBN --------------------------------------------------------------------------
    BookEntity updateBookByIsbnPartial(String isbn, BookEntity bookEntity);

    void deleteBook(String isbn);

}
