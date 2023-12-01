package com.coldcoffee.demo.repository;
import com.coldcoffee.demo.TestDataUtility;
import com.coldcoffee.demo.model.Author;
import org.checkerframework.checker.units.qual.A;
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
public class AuthorRepositoryIntegrationTest {

    private final AuthorRepository authorRepositoryUnderTest;

    @Autowired
    public AuthorRepositoryIntegrationTest(AuthorRepository authorRepositoryUnderTest) {
        this.authorRepositoryUnderTest = authorRepositoryUnderTest;
    }

    @Test
    public void testThat__OneAuthorCanBeCreatedAndFetched() {
        Author author = TestDataUtility.createTestAuthorOne();
        authorRepositoryUnderTest.save(author);
        Optional<Author> result = authorRepositoryUnderTest.findById(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThat__ManyAuthorsCanBeCreatedAndFetched() {
        Author authorOne = TestDataUtility.createTestAuthorOne();
        authorRepositoryUnderTest.save(authorOne);
        Author authorTwo = TestDataUtility.createTestAuthorTwo();
        authorRepositoryUnderTest.save(authorTwo);
        Author authorThree = TestDataUtility.createTestAuthorThree();
        authorRepositoryUnderTest.save(authorThree);
        Iterable<Author> result = authorRepositoryUnderTest.findAll();
        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(authorOne, authorTwo, authorThree);
    }

    @Test
    public void testThat__OneAuthorCanBeUpdated() {
        Author authorOne = TestDataUtility.createTestAuthorOne();
        authorRepositoryUnderTest.save(authorOne);
        authorOne.setName("UPDATED");
        authorRepositoryUnderTest.save(authorOne); // --> save is used for both creating and updating in JPA
        Optional<Author> result = authorRepositoryUnderTest.findById(authorOne.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorOne);
    }
    @Test
    public void testThat__OneAuthorCanBeDeleted() {
        Author authorOne = TestDataUtility.createTestAuthorOne();
        authorRepositoryUnderTest.save(authorOne);
        authorRepositoryUnderTest.deleteById(authorOne.getId());
        Optional<Author> result = authorRepositoryUnderTest.findById(authorOne.getId());
        assertThat(result).isEmpty();
    }


    // this is a test to show that JPA can automatically generate the SQL queries based on the entities and fields.
    @Test
    public void testThat__AuthorsWithAgeLessThan() {
        Author authorOne = TestDataUtility.createTestAuthorOne();
        authorRepositoryUnderTest.save(authorOne);
        Author authorTwo = TestDataUtility.createTestAuthorTwo();
        authorRepositoryUnderTest.save(authorTwo);
        Author authorThree = TestDataUtility.createTestAuthorThree();
        authorRepositoryUnderTest.save(authorThree);
        Iterable<Author> result = authorRepositoryUnderTest.ageLessThan(35);
        assertThat(result).containsExactly(authorOne, authorTwo);
    }

    // but it has its limits so this test will showcase some HQL we can use to help JPA figure out the query.
    @Test
    public void testThat__GetAuthorsWithAgeGreaterThan() {
        Author authorOne = TestDataUtility.createTestAuthorOne();
        authorRepositoryUnderTest.save(authorOne);
        Author authorTwo = TestDataUtility.createTestAuthorTwo();
        authorRepositoryUnderTest.save(authorTwo);
        Author authorThree = TestDataUtility.createTestAuthorThree();
        authorRepositoryUnderTest.save(authorThree);
        // purposefully named to confuse JPA so we can use @Query and write out the HQL in the repository
        Iterable<Author> result = authorRepositoryUnderTest.findAuthorsWithAgeGreaterThan(39);
        assertThat(result).containsExactly(authorThree);


    }
}
