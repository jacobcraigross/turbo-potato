package com.coldcoffee.demo.repository;
import com.coldcoffee.demo.TestDataUtility;
import com.coldcoffee.demo.model.AuthorEntity;
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
public class AuthorEntityRepositoryIntegrationTest {

    private final AuthorRepository authorRepositoryUnderTest;

    @Autowired
    public AuthorEntityRepositoryIntegrationTest(AuthorRepository authorRepositoryUnderTest) {
        this.authorRepositoryUnderTest = authorRepositoryUnderTest;
    }

    @Test
    public void testThat__OneAuthorCanBeCreatedAndFetched() {
        AuthorEntity authorEntity = TestDataUtility.createTestAuthorEntityOne();
        authorRepositoryUnderTest.save(authorEntity);
        Optional<AuthorEntity> result = authorRepositoryUnderTest.findById(authorEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntity);
    }

    @Test
    public void testThat__ManyAuthorsCanBeCreatedAndFetched() {
        AuthorEntity authorEntityOne = TestDataUtility.createTestAuthorEntityOne();
        authorRepositoryUnderTest.save(authorEntityOne);
        AuthorEntity authorEntityTwo = TestDataUtility.createTestAuthorEntityTwo();
        authorRepositoryUnderTest.save(authorEntityTwo);
        AuthorEntity authorEntityThree = TestDataUtility.createTestAuthorEntityThree();
        authorRepositoryUnderTest.save(authorEntityThree);
        Iterable<AuthorEntity> result = authorRepositoryUnderTest.findAll();
        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(authorEntityOne, authorEntityTwo, authorEntityThree);
    }

    @Test
    public void testThat__OneAuthorCanBeUpdated() {
        AuthorEntity authorEntityOne = TestDataUtility.createTestAuthorEntityOne();
        authorRepositoryUnderTest.save(authorEntityOne);
        authorEntityOne.setName("UPDATED");
        authorRepositoryUnderTest.save(authorEntityOne); // --> save is used for both creating and updating in JPA
        Optional<AuthorEntity> result = authorRepositoryUnderTest.findById(authorEntityOne.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntityOne);
    }
    @Test
    public void testThat__OneAuthorCanBeDeleted() {
        AuthorEntity authorEntityOne = TestDataUtility.createTestAuthorEntityOne();
        authorRepositoryUnderTest.save(authorEntityOne);
        authorRepositoryUnderTest.deleteById(authorEntityOne.getId());
        Optional<AuthorEntity> result = authorRepositoryUnderTest.findById(authorEntityOne.getId());
        assertThat(result).isEmpty();
    }


    // this is a test to show that JPA can automatically generate the SQL queries based on the entities and fields.
    @Test
    public void testThat__AuthorsWithAgeLessThan() {
        AuthorEntity authorEntityOne = TestDataUtility.createTestAuthorEntityOne();
        authorRepositoryUnderTest.save(authorEntityOne);
        AuthorEntity authorEntityTwo = TestDataUtility.createTestAuthorEntityTwo();
        authorRepositoryUnderTest.save(authorEntityTwo);
        AuthorEntity authorEntityThree = TestDataUtility.createTestAuthorEntityThree();
        authorRepositoryUnderTest.save(authorEntityThree);
        Iterable<AuthorEntity> result = authorRepositoryUnderTest.ageLessThan(35);
        assertThat(result).containsExactly(authorEntityOne, authorEntityTwo);
    }

    // but it has its limits so this test will showcase some HQL we can use to help JPA figure out the query.
    @Test
    public void testThat__GetAuthorsWithAgeGreaterThan() {
        AuthorEntity authorEntityOne = TestDataUtility.createTestAuthorEntityOne();
        authorRepositoryUnderTest.save(authorEntityOne);
        AuthorEntity authorEntityTwo = TestDataUtility.createTestAuthorEntityTwo();
        authorRepositoryUnderTest.save(authorEntityTwo);
        AuthorEntity authorEntityThree = TestDataUtility.createTestAuthorEntityThree();
        authorRepositoryUnderTest.save(authorEntityThree);
        // purposefully named to confuse JPA so we can use @Query and write out the HQL in the repository
        Iterable<AuthorEntity> result = authorRepositoryUnderTest.findAuthorsWithAgeGreaterThan(39);
        assertThat(result).containsExactly(authorEntityThree);


    }
}
