//package com.coldcoffee.demo.repository;
//import com.coldcoffee.demo.TestDataUtility;
//import com.coldcoffee.demo.model.Author;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import java.util.List;
//import java.util.Optional;
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//public class AuthorDAO_IMPL_IntegrationTest {
//
//    private final AuthorDAOImplementation authorDAOImplementationUnderTest;
//
//    @Autowired
//    public AuthorDAO_IMPL_IntegrationTest(AuthorDAOImplementation authorDAOImplementationUnderTest) {
//        this.authorDAOImplementationUnderTest = authorDAOImplementationUnderTest;
//    }
//
//    @Test
//    public void testThat__OneAuthorCanBeCreatedAndFetched() {
//        Author author = TestDataUtility.createTestAuthorOne();
//        authorDAOImplementationUnderTest.createAuthor(author);
//        Optional<Author> result = authorDAOImplementationUnderTest.findOneAuthor(author.getId());
//        assertThat(result).isPresent();
//        assertThat(result.get()).isEqualTo(author);
//    }
//
//    @Test
//    public void testThat__ManyAuthorsCanBeCreatedAndFetched() {
//        Author authorOne = TestDataUtility.createTestAuthorOne();
//        authorDAOImplementationUnderTest.createAuthor(authorOne);
//        Author authorTwo = TestDataUtility.createTestAuthorTwo();
//        authorDAOImplementationUnderTest.createAuthor(authorTwo);
//        Author authorThree = TestDataUtility.createTestAuthorThree();
//        authorDAOImplementationUnderTest.createAuthor(authorThree);
//        List<Author> result = authorDAOImplementationUnderTest.findAllAuthors();
//        assertThat(result).hasSize(3);
//        assertThat(result).containsExactly(authorOne, authorTwo, authorThree);
//    }
//
//    @Test
//    public void testThat__OneAuthorCanBeUpdated() {
//        Author authorOne = TestDataUtility.createTestAuthorOne();
//        authorDAOImplementationUnderTest.createAuthor(authorOne);
//        authorOne.setName("UPDATED");
//        authorDAOImplementationUnderTest.updateAuthor(authorOne.getId(), authorOne);
//        Optional<Author> result = authorDAOImplementationUnderTest.findOneAuthor(authorOne.getId());
//        assertThat(result).isPresent();
//        assertThat(result.get()).isEqualTo(authorOne);
//    }
//
//    @Test
//    public void testThat__OneAuthorCanBeDeleted() {
//        Author authorOne = TestDataUtility.createTestAuthorOne();
//        authorDAOImplementationUnderTest.createAuthor(authorOne);
//        authorDAOImplementationUnderTest.deleteAuthor(authorOne.getId());
//        Optional<Author> result = authorDAOImplementationUnderTest.findOneAuthor(authorOne.getId());
//        assertThat(result).isEmpty();
//    }
//}
