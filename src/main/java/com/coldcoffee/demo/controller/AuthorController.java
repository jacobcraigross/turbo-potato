package com.coldcoffee.demo.controller;
import com.coldcoffee.demo.DTO.AuthorDTO;
import com.coldcoffee.demo.mapper.Mapper;
import com.coldcoffee.demo.model.AuthorEntity;
import com.coldcoffee.demo.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
public class AuthorController {

    // ---------- CONSTRUCTOR INJECTION --------------------------------------------------------------------------------
    private final AuthorService authorService;
    private final Mapper<AuthorEntity, AuthorDTO> authorMapper;
    @Autowired
    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDTO> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }



    // ---------- CREATE AUTHOR ----------------------------------------------------------------------------------------
    @PostMapping("/authors")
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO) {
        AuthorEntity authorEntity = authorMapper.mapFromDTOToEntity(authorDTO);
        AuthorEntity savedAuthorEntity = authorService.createAuthor(authorEntity);
        return new ResponseEntity<>(authorMapper.mapFromEntityToDTO(savedAuthorEntity), HttpStatus.CREATED);
    }


    // ---------- GET ALL AUTHORS --------------------------------------------------------------------------------------
    @GetMapping("/authors")
    public List<AuthorDTO> getAllAuthors() {
        List<AuthorEntity> authors = authorService.getAllAuthors();
        return authors.stream().map(authorMapper::mapFromEntityToDTO).collect(Collectors.toList());
    }


    // ---------- GET AUTHOR BY ID -------------------------------------------------------------------------------------
    @GetMapping("/authors/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable("id") Long id) {
        Optional<AuthorEntity> foundAuthor = authorService.getAuthorById(id);
        return foundAuthor.map(authorEntity -> {
            AuthorDTO authorDTO = authorMapper.mapFromEntityToDTO(authorEntity);
            return new ResponseEntity<>(authorDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    // ---------- UPDATE AUTHOR ----------------------------------------------------------------------------------------



    // ---------- DELETE AUTHOR ----------------------------------------------------------------------------------------


}
