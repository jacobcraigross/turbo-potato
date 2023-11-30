package com.coldcoffee.demo;
import com.coldcoffee.demo.model.Author;
import com.coldcoffee.demo.model.Book;

public final class TestDataUtility {
    private TestDataUtility(){}

    public static Author createTestAuthorOne() {
        return Author.builder()
                .id(1L)
                .name("Jake")
                .age(18)
                .build();
    }

    public static Author createTestAuthorTwo() {
        return Author.builder()
                .id(2L)
                .name("Lauren")
                .age(19)
                .build();
    }

    public static Author createTestAuthorThree() {
        return Author.builder()
                .id(3L)
                .name("Joelle")
                .age(20)
                .build();
    }



    public static Book createTestBookOne() {
        return Book.builder()
                .isbn("123")
                .title("Blood")
                .authorId(1L)
                .build();
    }

    public static Book createTestBookTwo() {
        return Book.builder()
                .isbn("456")
                .title("Fire")
                .authorId(1L)
                .build();
    }

    public static Book createTestBookThree() {
        return Book.builder()
                .isbn("789")
                .title("Death")
                .authorId(1L)
                .build();
    }
}
