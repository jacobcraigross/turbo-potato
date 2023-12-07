package com.coldcoffee.demo.service;
import com.coldcoffee.demo.model.AuthorEntity;
import com.coldcoffee.demo.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class AuthorServiceIMPL implements AuthorService {

    // ---------- CONSTRUCTOR INJECTION --------------------------------------------------------------------------------
    private final AuthorRepository authorRepository;
    public AuthorServiceIMPL(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    // ---------- CREATE AUTHOR ----------------------------------------------------------------------------------------
    @Override
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }


    // ---------- GET ALL AUTHORS --------------------------------------------------------------------------------------
    @Override
    public List<AuthorEntity> getAllAuthors() {
        return StreamSupport.stream(authorRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }


    // ---------- GET AUTHOR BY ID -------------------------------------------------------------------------------------
    @Override
    public Optional<AuthorEntity> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }
}
