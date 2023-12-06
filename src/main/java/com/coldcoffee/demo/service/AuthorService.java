package com.coldcoffee.demo.service;
import com.coldcoffee.demo.model.AuthorEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorService {
    AuthorEntity createAuthor(AuthorEntity authorEntity);

    List<AuthorEntity> getAllAuthors();
}
