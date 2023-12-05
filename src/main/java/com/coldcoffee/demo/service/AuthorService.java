package com.coldcoffee.demo.service;
import com.coldcoffee.demo.model.AuthorEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthorService {
    AuthorEntity createAuthor(AuthorEntity authorEntity);
}
