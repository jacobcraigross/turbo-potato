package com.coldcoffee.demo.service;
import com.coldcoffee.demo.model.AuthorEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public interface AuthorService {
    AuthorEntity createAuthor(AuthorEntity authorEntity);

    List<AuthorEntity> getAllAuthors();

    Optional<AuthorEntity> getAuthorById(Long id);

}
