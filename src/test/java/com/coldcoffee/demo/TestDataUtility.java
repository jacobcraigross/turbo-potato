package com.coldcoffee.demo;
import com.coldcoffee.demo.model.Author;
import com.coldcoffee.demo.model.Book;

public final class TestDataUtility {
    private TestDataUtility(){}

    public static Author createTestAuthorOne() {
        return Author.builder()
                .id(1L)
                .name("Jake")
                .age(20)
                .build();
    }

    public static Author createTestAuthorTwo() {
        return Author.builder()
                .id(2L)
                .name("Lauren")
                .age(30)
                .build();
    }

    public static Author createTestAuthorThree() {
        return Author.builder()
                .id(3L)
                .name("Joelle")
                .age(40)
                .build();
    }



    public static Book createTestBookOne(Author author) {
        return Book.builder()
                .isbn("123")
                .title("Blood")
                .author(author)
                .build();
    }

    public static Book createTestBookTwo(Author author) {
        return Book.builder()
                .isbn("456")
                .title("Fire")
                .author(author)
                .build();
    }

    public static Book createTestBookThree(Author author) {
        return Book.builder()
                .isbn("789")
                .title("Death")
                .author(author)
                .build();
    }
}
