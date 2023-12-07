package com.coldcoffee.demo;
import com.coldcoffee.demo.DTO.AuthorDTO;
import com.coldcoffee.demo.DTO.BookDTO;
import com.coldcoffee.demo.model.AuthorEntity;
import com.coldcoffee.demo.model.BookEntity;

public final class TestDataUtility {
    private TestDataUtility(){}

    public static AuthorEntity createTestAuthorEntityOne() {
        return AuthorEntity.builder()
                .id(1L)
                .name("Jake")
                .age(20)
                .build();
    }

    public static AuthorEntity createTestAuthorEntityTwo() {
        return AuthorEntity.builder()
                .id(2L)
                .name("Lauren")
                .age(30)
                .build();
    }

    public static AuthorEntity createTestAuthorEntityThree() {
        return AuthorEntity.builder()
                .id(3L)
                .name("Joelle")
                .age(40)
                .build();
    }

    public static AuthorDTO createTestAuthorDTO(AuthorDTO authorDTO) {
        return AuthorDTO.builder()
                .id(4L)
                .name("Four Long")
                .age(44)
                .build();
    }



    public static BookEntity createTestBookEntityOne(AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("123")
                .title("Jake")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookEntity createTestBookEntityTwo(AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("456")
                .title("Fire")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookEntity createTestBookEntityThree(AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("789")
                .title("Death")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookDTO createTestBookDTO(AuthorDTO authorDTO) {
        return BookDTO.builder()
                .isbn("666")
                .title("Sixer")
                .authorDTO(authorDTO)
                .build();
    }
}
