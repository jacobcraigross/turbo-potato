package com.coldcoffee.demo.DAO;
import com.coldcoffee.demo.model.Book;

import java.util.List;
import java.util.Optional;


public interface BookDAO {
    void createBook(Book book);

    Optional<Book> findOneBook(String isbn);

    List<Book> findAllBooks();

    void updateBook(String isbn, Book book);

    void deleteBook(String isbn);
}
