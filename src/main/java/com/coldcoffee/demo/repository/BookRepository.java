package com.coldcoffee.demo.repository;
import com.coldcoffee.demo.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {
}
