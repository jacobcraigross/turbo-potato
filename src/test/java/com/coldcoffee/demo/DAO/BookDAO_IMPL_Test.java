package com.coldcoffee.demo.DAO;
import com.coldcoffee.demo.TestDataUtility;
import com.coldcoffee.demo.model.Author;
import com.coldcoffee.demo.model.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class BookDAO_IMPL_Test {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDAOImplementation bookDAOImplementationUnderTest;

    // ----------------------------------------------------------------------------------------------------------------

    @Test
    public void testThat__CreateOneBook__GeneratesCorrectSQL() {
        Book book = Book.builder()
                .isbn("123")
                .title("Blood")
                .authorId(2L)
                .build();

        bookDAOImplementationUnderTest.createBook(book);
        verify(jdbcTemplate).update(
                eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"),
                eq("123"), eq("Blood"), eq(2L)
        );
    }

    // ----------------------------------------------------------------------------------------------------------------

    @Test
    public void testThat__FindOneBook__GeneratesCorrectSQL() {
        bookDAOImplementationUnderTest.findOneBook("123");
        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1"),
                ArgumentMatchers.<BookDAOImplementation.BookRowMapper>any(),
                eq("123"));
    }

    // ----------------------------------------------------------------------------------------------------------------


    @Test
    public void testThat__FindAllBooks__GeneratesCorrectSQL(){
        bookDAOImplementationUnderTest.findAllBooks();
        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books"),
                ArgumentMatchers.<BookDAOImplementation.BookRowMapper>any()
        );
    }

    // ----------------------------------------------------------------------------------------------------------------

    @Test
    public void testThat__UpdateOneBook__GeneratesCorrectSQL() {
        Book book = TestDataUtility.createTestBookOne();
        bookDAOImplementationUnderTest.updateBook("123", book);
        verify(jdbcTemplate).update("UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?", "123", "Blood", 1L, "123");
    }

    // ----------------------------------------------------------------------------------------------------------------

    @Test
    public void testThat__DeleteOneBook__GeneratesCorrectSQL() {
        bookDAOImplementationUnderTest.deleteBook("123");
        verify(jdbcTemplate).update("DELETE FROM books WHERE isbn = ?", "123");
    }


}