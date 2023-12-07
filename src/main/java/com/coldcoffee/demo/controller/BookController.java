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


    // ---------- CREATE BOOK ------------------------------------------------------------------------------------------
    @PutMapping("/books/{isbn}") // PUT mapping because the ISBN (design dec)
    public ResponseEntity<BookDTO> createBook(@PathVariable("isbn") String isbn, @RequestBody BookDTO bookDTO) {
        BookEntity bookEntity = bookMapper.mapFromDTOToEntity(bookDTO);
        BookEntity savedBookEntity = bookService.createBook(isbn, bookEntity);
        BookDTO savedBookDTO = bookMapper.mapFromEntityToDTO(savedBookEntity);
        return new ResponseEntity<>(savedBookDTO, HttpStatus.CREATED);
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

}
