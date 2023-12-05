package com.coldcoffee.demo;
import com.coldcoffee.demo.model.AuthorEntity;
import com.coldcoffee.demo.model.BookEntity;

public final class TestDataUtility {
    private TestDataUtility(){}

    public static AuthorEntity createTestAuthorOne() {
        return AuthorEntity.builder()
                .id(1L)
                .name("Jake")
                .age(20)
                .build();
    }

    public static AuthorEntity createTestAuthorTwo() {
        return AuthorEntity.builder()
                .id(2L)
                .name("Lauren")
                .age(30)
                .build();
    }

    public static AuthorEntity createTestAuthorThree() {
        return AuthorEntity.builder()
                .id(3L)
                .name("Joelle")
                .age(40)
                .build();
    }



    public static BookEntity createTestBookOne(AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("123")
                .title("Blood")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookEntity createTestBookTwo(AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("456")
                .title("Fire")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookEntity createTestBookThree(AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("789")
                .title("Death")
                .authorEntity(authorEntity)
                .build();
    }
}
