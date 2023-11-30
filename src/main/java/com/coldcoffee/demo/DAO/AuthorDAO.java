package com.coldcoffee.demo.DAO;
import com.coldcoffee.demo.model.Author;
import java.util.List;
import java.util.Optional;



public interface AuthorDAO {
    void createAuthor(Author author);

    Optional<Author> findOneAuthor(long l);

    List<Author> findAllAuthors();

    void updateAuthor(long id, Author author);

    void deleteAuthor(long id);
}
