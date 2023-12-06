package com.coldcoffee.demo.service;

import com.coldcoffee.demo.model.BookEntity;
import org.springframework.stereotype.Service;

@Service
public interface BookService {
    BookEntity createBook(String isbn, BookEntity bookEntity);

}
