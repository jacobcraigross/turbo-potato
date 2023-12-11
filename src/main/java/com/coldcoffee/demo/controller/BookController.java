package com.coldcoffee.demo.controller;
import com.coldcoffee.demo.DTO.AuthorDTO;
import com.coldcoffee.demo.DTO.BookDTO;
import com.coldcoffee.demo.mapper.Mapper;
import com.coldcoffee.demo.model.AuthorEntity;
import com.coldcoffee.demo.model.BookEntity;
import com.coldcoffee.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {


    // ---------- CONSTRUCTOR INJECTION --------------------------------------------------------------------------------
    private final Mapper<BookEntity, BookDTO> bookMapper;
    private final BookService bookService;
    @Autowired
    public BookController(Mapper<BookEntity, BookDTO> bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }


    // ---------- CREATE OR UPDATE BOOK --------------------------------------------------------------------------------
    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDTO> createOrUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDTO bookDTO) {

        BookEntity bookEntity = bookMapper.mapFromDTOToEntity(bookDTO);
        boolean bookExists = bookService.doesBookExist(isbn);
        BookEntity savedBookEntity = bookService.createOrUpdateBook(isbn, bookEntity);
        BookDTO savedBookDTO = bookMapper.mapFromEntityToDTO(savedBookEntity);

        if (bookExists) {
            // update
            return new ResponseEntity<>(savedBookDTO, HttpStatus.OK);
        } else {
            // create
            return new ResponseEntity<>(savedBookDTO, HttpStatus.CREATED);
        }

    }


    // ---------- GET ALL BOOKS ----------------------------------------------------------------------------------------
    @GetMapping("/books")
    public List<BookDTO> getAllBooks() {
        List<BookEntity> books = bookService.getAllBooks();
        return books.stream().map(bookMapper::mapFromEntityToDTO).collect(java.util.stream.Collectors.toList());
    }


    // ---------- GET BOOK BY ISBN -------------------------------------------------------------------------------------
    @GetMapping("/books/{isbn}")
    public ResponseEntity<BookDTO> getBookByIsbn(@PathVariable("isbn") String isbn) {
        Optional<BookEntity> foundBook = bookService.getBookByIsbn(isbn);
        return foundBook.map(bookEntity -> {
            BookDTO bookDTO = bookMapper.mapFromEntityToDTO(bookEntity);
            return new ResponseEntity<>(bookDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // ---------- PARTIAL UPDATE BOOK BY ISBN --------------------------------------------------------------------------
    @PatchMapping("/books/{isbn}")
    public ResponseEntity<BookDTO> updateBookByIsbnPartial(@PathVariable("isbn") String isbn, @RequestBody BookDTO bookDTO) {
        if (bookService.getBookByIsbn(isbn).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            bookDTO.setIsbn(isbn);
            BookEntity bookEntity = bookMapper.mapFromDTOToEntity(bookDTO);
            BookEntity updatedBookEntity = bookService.updateBookByIsbnPartial(isbn, bookEntity);
            return new ResponseEntity<>(bookMapper.mapFromEntityToDTO(updatedBookEntity), HttpStatus.OK);
        }
    }

    // ---------- DELETE BOOK ------------------------------------------------------------------------------------------
    @DeleteMapping("/books/{isbn}")
    public ResponseEntity<Void> deleteBook(@PathVariable("isbn") String isbn) {
        bookService.deleteBook(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
