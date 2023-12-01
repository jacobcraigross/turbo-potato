package com.coldcoffee.demo.repository;
import com.coldcoffee.demo.TestDataUtility;
import com.coldcoffee.demo.model.Author;
import com.coldcoffee.demo.model.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTest {
    private final BookRepository bookRepositoryUnderTest;

    @Autowired
    public BookRepositoryIntegrationTest(BookRepository bookRepositoryUnderTest) {
        this.bookRepositoryUnderTest = bookRepositoryUnderTest;
    }

    @Test
    public void testThat__OneBookCanBeCreatedAndFetched() {
        Author authorOne = TestDataUtility.createTestAuthorOne();
        Book bookOne = TestDataUtility.createTestBookOne(authorOne);
        bookRepositoryUnderTest.save(bookOne);
        Optional<Book> result = bookRepositoryUnderTest.findById(bookOne.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookOne);
    }

    @Test
    public void testThat__ManyBooksCanBeCreatedAndFetched() {
        Author authorOne = TestDataUtility.createTestAuthorOne();
        Book bookOne = TestDataUtility.createTestBookOne(authorOne);
        bookRepositoryUnderTest.save(bookOne);
        Book bookTwo = TestDataUtility.createTestBookTwo(authorOne);
        bookRepositoryUnderTest.save(bookTwo);
        Book bookThree = TestDataUtility.createTestBookThree(authorOne);
        bookRepositoryUnderTest.save(bookThree);
        Iterable<Book> result = bookRepositoryUnderTest.findAll();
        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(bookOne, bookTwo, bookThree);
    }

    @Test
    public void TestThat__OneBookCanBeUpdated() {
        Author authorOne = TestDataUtility.createTestAuthorOne();
        Book bookOne = TestDataUtility.createTestBookOne(authorOne);
        bookRepositoryUnderTest.save(bookOne);
        bookOne.setTitle("UPDATED");
        bookRepositoryUnderTest.save(bookOne);
        Optional<Book> result = bookRepositoryUnderTest.findById(bookOne.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookOne);
    }

    @Test
    public void testThat__OneBookCanBeDeleted() {
        Author authorOne = TestDataUtility.createTestAuthorOne();
        Book bookOne = TestDataUtility.createTestBookOne(authorOne);
        bookRepositoryUnderTest.save(bookOne);
        bookRepositoryUnderTest.deleteById(bookOne.getIsbn());
        Optional<Book> result = bookRepositoryUnderTest.findById(bookOne.getIsbn());
        assertThat(result).isEmpty();
    }

}
