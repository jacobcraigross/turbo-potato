package com.coldcoffee.demo.service;

import com.coldcoffee.demo.model.AuthorEntity;
import com.coldcoffee.demo.repository.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceIMPL implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceIMPL(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }
}
