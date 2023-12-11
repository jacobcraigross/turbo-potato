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

    // ---------- UPDATE AUTHOR BY ID ----------------------------------------------------------------------------------
    @Override
    public AuthorEntity updateAuthorById(Long id, AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

    // ---------- UPDATE AUTHOR BY ID (PARTIAL) ------------------------------------------------------------------------
    @Override
    public AuthorEntity updateAuthorByIdPartial(Long id, AuthorEntity authorEntity) {
        return authorRepository.findById(id).map(existingAuthor -> {
            Optional.ofNullable(authorEntity.getName()).ifPresent(existingAuthor::setName);
            Optional.ofNullable(authorEntity.getAge()).ifPresent(existingAuthor::setAge);
            return authorRepository.save(existingAuthor);
        }).orElseThrow(() -> new RuntimeException("Author doesnt exist"));
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
