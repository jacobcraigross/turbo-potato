package com.coldcoffee.demo.DAO;
import com.coldcoffee.demo.TestDataUtility;
import com.coldcoffee.demo.model.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import static com.coldcoffee.demo.TestDataUtility.createTestAuthorOne;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class AuthorDAO_IMPL_Test {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDAOImplementation authorDAOImplementationUnderTest;

    // ----------------------------------------------------------------------------------------------------------------
    /*
    These tests for now are just testing that the SQL queries we build are generating the correct SQL.
    Nothing is being tested against actual data in the database just yet. That will come later.
    */

    @Test
    public void testThat__CreateOneAuthor__GeneratesCorrectSQL() {
        Author author = createTestAuthorOne();
        authorDAOImplementationUnderTest.createAuthor(author);
        verify(jdbcTemplate).update(
                eq("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)"),
                eq(1L), eq("Jake"), eq(18)
        );
    }

    // ----------------------------------------------------------------------------------------------------------------

    @Test
    public void testThat__FindOneAuthor__GeneratesCorrectSQL() {
        authorDAOImplementationUnderTest.findOneAuthor(1L);
        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors WHERE id = ? LIMIT 1"),
                ArgumentMatchers.<AuthorDAOImplementation.AuthorRowMapper>any(),
                eq(1L)
        );
    }

    // ----------------------------------------------------------------------------------------------------------------

    @Test
    public void testThat__FindAllAuthors__GeneratesCorrectSQL() {
        authorDAOImplementationUnderTest.findAllAuthors();
        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors"),
                ArgumentMatchers.<AuthorDAOImplementation.AuthorRowMapper>any()
        );
    }

    // ----------------------------------------------------------------------------------------------------------------

    @Test
    public void testThat__UpdateOneAuthor__GeneratesCorrectSQL() {
        Author author = TestDataUtility.createTestAuthorOne();
        authorDAOImplementationUnderTest.updateAuthor(3L, author);
        verify(jdbcTemplate).update("UPDATE authors SET id = ?, name = ?, age = ? WHERE id = ?", 1L, "Jake", 18, 3L);
    }

    // ----------------------------------------------------------------------------------------------------------------

    @Test
    public void testThat__DeleteOneAuthor__GeneratesCorrectSQL() {
        authorDAOImplementationUnderTest.deleteAuthor(1L);
        verify(jdbcTemplate).update("DELETE FROM authors WHERE id = ?", 1L);
    }



}
