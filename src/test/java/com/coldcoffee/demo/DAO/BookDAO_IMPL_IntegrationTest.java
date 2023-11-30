package com.coldcoffee.demo.DAO;
import com.coldcoffee.demo.TestDataUtility;
import com.coldcoffee.demo.model.Author;
import com.coldcoffee.demo.model.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookDAO_IMPL_IntegrationTest {

    private final AuthorDAO authorDAO;
    private final BookDAOImplementation bookDAOImplementationUnderTest;

    @Autowired
    public BookDAO_IMPL_IntegrationTest(AuthorDAO authorDAO, BookDAOImplementation bookDAOImplementationUnderTest) {
        this.authorDAO = authorDAO;
        this.bookDAOImplementationUnderTest = bookDAOImplementationUnderTest;
    }

    @Test
    public void testThat__OneBookCanBeCreatedAndFetched() {
        Author author = TestDataUtility.createTestAuthorOne();
        authorDAO.createAuthor(author);
        Book book = TestDataUtility.createTestBookOne();
        book.setAuthorId(author.getId());
        bookDAOImplementationUnderTest.createBook(book);
        Optional<Book> result = bookDAOImplementationUnderTest.findOneBook(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThat__ManyBooksCanBeCreatedAndFetched() {
        Author authorOne = TestDataUtility.createTestAuthorOne();
        authorDAO.createAuthor(authorOne);
        Book bookOne = TestDataUtility.createTestBookOne();
        bookOne.setAuthorId(bookOne.getAuthorId());
        bookDAOImplementationUnderTest.createBook(bookOne);
        Book bookTwo = TestDataUtility.createTestBookTwo();
        bookTwo.setAuthorId(bookTwo.getAuthorId());
        bookDAOImplementationUnderTest.createBook(bookTwo);
        Book bookThree = TestDataUtility.createTestBookThree();
        bookThree.setAuthorId(bookThree.getAuthorId());
        bookDAOImplementationUnderTest.createBook(bookThree);
        List<Book> result = bookDAOImplementationUnderTest.findAllBooks();
        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(bookOne, bookTwo, bookThree);
    }

    @Test
    public void TestThat__OneBookCanBeUpdated() {
        Author authorOne = TestDataUtility.createTestAuthorOne();
        authorDAO.createAuthor(authorOne);
        Book bookOne = TestDataUtility.createTestBookOne();
        bookOne.setAuthorId(bookOne.getAuthorId());
        bookDAOImplementationUnderTest.createBook(bookOne);
        bookOne.setTitle("UPDATED");
        bookDAOImplementationUnderTest.updateBook(bookOne.getIsbn(), bookOne);
        Optional<Book> result = bookDAOImplementationUnderTest.findOneBook(bookOne.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookOne);
    }

    @Test
    public void testThat__OneBookCanBeDeleted() {
        Author authorOne = TestDataUtility.createTestAuthorOne();
        authorDAO.createAuthor(authorOne);
        Book bookOne = TestDataUtility.createTestBookOne();
        bookDAOImplementationUnderTest.createBook(bookOne);
        bookDAOImplementationUnderTest.deleteBook(bookOne.getIsbn());
        Optional<Book> result = bookDAOImplementationUnderTest.findOneBook(bookOne.getIsbn());
        assertThat(result).isEmpty();
    }

}
