package com.coldcoffee.demo.controller;
import com.coldcoffee.demo.DTO.BookDTO;
import com.coldcoffee.demo.mapper.Mapper;
import com.coldcoffee.demo.model.BookEntity;
import com.coldcoffee.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private final Mapper<BookEntity, BookDTO> bookMapper;

    private final BookService bookService;

    @Autowired
    public BookController(Mapper<BookEntity, BookDTO> bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @PutMapping("/books/{isbn}") // PUT mapping because the ISBN (design dec)
    public ResponseEntity<BookDTO> createBook(@PathVariable("isbn") String isbn, @RequestBody BookDTO bookDTO) {
        BookEntity bookEntity = bookMapper.mapFromDTOToEntity(bookDTO);
        BookEntity savedBookEntity = bookService.createBook(isbn, bookEntity);
        BookDTO savedBookDTO = bookMapper.mapFromEntityToDTO(savedBookEntity);
        return new ResponseEntity<>(savedBookDTO, HttpStatus.CREATED);
    }

}
