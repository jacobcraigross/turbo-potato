package com.coldcoffee.demo.repository;
import com.coldcoffee.demo.TestDataUtility;
import com.coldcoffee.demo.model.AuthorEntity;
import com.coldcoffee.demo.model.BookEntity;
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
public class BookEntityRepositoryIntegrationTest {
    private final BookRepository bookRepositoryUnderTest;

    @Autowired
    public BookEntityRepositoryIntegrationTest(BookRepository bookRepositoryUnderTest) {
        this.bookRepositoryUnderTest = bookRepositoryUnderTest;
    }

    @Test
    public void testThat__OneBookCanBeCreatedAndFetched() {
        AuthorEntity authorEntityOne = TestDataUtility.createTestAuthorEntityOne();
        BookEntity bookEntityOne = TestDataUtility.createTestBookEntityOne(authorEntityOne);
        bookRepositoryUnderTest.save(bookEntityOne);
        Optional<BookEntity> result = bookRepositoryUnderTest.findById(bookEntityOne.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntityOne);
    }

    @Test
    public void testThat__ManyBooksCanBeCreatedAndFetched() {
        AuthorEntity authorEntityOne = TestDataUtility.createTestAuthorEntityOne();
        BookEntity bookEntityOne = TestDataUtility.createTestBookEntityOne(authorEntityOne);
        bookRepositoryUnderTest.save(bookEntityOne);
        BookEntity bookEntityTwo = TestDataUtility.createTestBookEntityTwo(authorEntityOne);
        bookRepositoryUnderTest.save(bookEntityTwo);
        BookEntity bookEntityThree = TestDataUtility.createTestBookEntityThree(authorEntityOne);
        bookRepositoryUnderTest.save(bookEntityThree);
        Iterable<BookEntity> result = bookRepositoryUnderTest.findAll();
        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(bookEntityOne, bookEntityTwo, bookEntityThree);
    }

    @Test
    public void TestThat__OneBookCanBeUpdated() {
        AuthorEntity authorEntityOne = TestDataUtility.createTestAuthorEntityOne();
        BookEntity bookEntityOne = TestDataUtility.createTestBookEntityOne(authorEntityOne);
        bookRepositoryUnderTest.save(bookEntityOne);
        bookEntityOne.setTitle("UPDATED");
        bookRepositoryUnderTest.save(bookEntityOne);
        Optional<BookEntity> result = bookRepositoryUnderTest.findById(bookEntityOne.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntityOne);
    }

    @Test
    public void testThat__OneBookCanBeDeleted() {
        AuthorEntity authorEntityOne = TestDataUtility.createTestAuthorEntityOne();
        BookEntity bookEntityOne = TestDataUtility.createTestBookEntityOne(authorEntityOne);
        bookRepositoryUnderTest.save(bookEntityOne);
        bookRepositoryUnderTest.deleteById(bookEntityOne.getIsbn());
        Optional<BookEntity> result = bookRepositoryUnderTest.findById(bookEntityOne.getIsbn());
        assertThat(result).isEmpty();
    }

}
