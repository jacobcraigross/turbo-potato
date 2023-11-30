package com.coldcoffee.demo.DAO;
import com.coldcoffee.demo.model.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;



@Component
public class AuthorDAOImplementation implements AuthorDAO {
    private final JdbcTemplate jdbcTemplate;
    public AuthorDAOImplementation(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public void createAuthor(Author author) {
        jdbcTemplate.update("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)", author.getId(), author.getName(), author.getAge());
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public Optional<Author> findOneAuthor(long authorId) {
        List<Author> result = jdbcTemplate.query("SELECT id, name, age FROM authors WHERE id = ? LIMIT 1", new AuthorRowMapper(), authorId);
        return result.stream().findFirst();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public List<Author> findAllAuthors() {
        return jdbcTemplate.query("SELECT id, name, age FROM authors", new AuthorRowMapper());
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public void updateAuthor(long id, Author author) {
        jdbcTemplate.update("UPDATE authors SET id = ?, name = ?, age = ? WHERE id = ?", author.getId(), author.getName(), author.getAge(), id);
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public void deleteAuthor(long id) {
        jdbcTemplate.update("DELETE FROM authors WHERE id = ?", 1L);
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static class AuthorRowMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Author.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .age(rs.getInt("age"))
                    .build();
        }
    }
}
