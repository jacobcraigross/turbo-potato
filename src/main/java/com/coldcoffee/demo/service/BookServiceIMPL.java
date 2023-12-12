package com.coldcoffee.demo.service;
import com.coldcoffee.demo.model.BookEntity;
import com.coldcoffee.demo.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class BookServiceIMPL implements BookService {


    // ---------- CONSTRUCTOR INJECTION --------------------------------------------------------------------------------
    private final BookRepository bookRepository;
    public BookServiceIMPL(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    // ---------- CREATE OR UPDATE BOOK ------------------------------------------------------------------------------------------
    @Override
    public BookEntity createOrUpdateBook(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);
        return bookRepository.save(bookEntity);
    }


    // ---------- GET ALL BOOKS ----------------------------------------------------------------------------------------
    @Override
    public List<BookEntity> getAllBooks() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    // ---------- GET ALL BOOKS PAGINATED ------------------------------------------------------------------------------
    @Override
    public Page<BookEntity> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }


    // ---------- GET BOOK BY ISBN -------------------------------------------------------------------------------------
    @Override
    public Optional<BookEntity> getBookByIsbn(String isbn) {
        return bookRepository.findById(isbn);
    }


    // ---------- DOES BOOK EXIST -------------------------------------------------------------------------------------
    @Override
    public boolean doesBookExist(String isbn) {
        return bookRepository.existsById(isbn);
    }

    // ---------- PARTIAL UPDATE BOOK BY ISBN --------------------------------------------------------------------------
    @Override
    public BookEntity updateBookByIsbnPartial(String isbn, BookEntity bookEntity) {
        return bookRepository.findById(isbn).map(existingBook -> {
            Optional.ofNullable(bookEntity.getTitle()).ifPresent(existingBook::setTitle);
            return bookRepository.save(existingBook);
        }).orElseThrow(() -> new RuntimeException("Book doesnt exist"));
    }

    @Override
    public void deleteBook(String isbn) {
        bookRepository.deleteById(isbn);
    }


}
