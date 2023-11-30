package com.coldcoffee.demo.DAO;
import com.coldcoffee.demo.model.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Component
public class BookDAOImplementation implements BookDAO {

    private final JdbcTemplate jdbcTemplate;

    public BookDAOImplementation(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createBook(Book book) {
        jdbcTemplate.update("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)", book.getIsbn(), book.getTitle(), book.getAuthorId());
    }

    // NOTE: jdbcTemplate.query() --> returns a List
    // hence that is why we use List<Book> and LIMIT 1 (even though it is only return ONE piece of data from the DB)
    @Override
    public Optional<Book> findOneBook(String isbn) {
        List<Book> result = jdbcTemplate.query("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1", new BookRowMapper(), isbn);
        return result.stream().findFirst();
    }

    @Override
    public List<Book> findAllBooks() {
        return jdbcTemplate.query("SELECT isbn, title, author_id FROM books", new BookRowMapper());
    }

    @Override
    public void updateBook(String isbn, Book book) {
        jdbcTemplate.update("UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?", book.getIsbn(), book.getTitle(), book.getAuthorId(), isbn);
    }

    @Override
    public void deleteBook(String isbn) {
        jdbcTemplate.update("DELETE FROM books WHERE isbn = ?", isbn);
    }


    public static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Book.builder()
                    .isbn(rs.getString("isbn"))
                    .title(rs.getString("title"))
                    .authorId(rs.getLong("author_id"))
                    .build();
        }
    }
}
