package com.coldcoffee.demo.repository;
import com.coldcoffee.demo.model.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
    Iterable<Author> ageLessThan(int age);

    @Query("SELECT author FROM Author author WHERE author.age > ?1")
    Iterable<Author> findAuthorsWithAgeGreaterThan(int age);
}







// --------------------------------------------------------------------------------------------------------------------
/*
In the query @Query("SELECT author FROM Author author WHERE author.age > ?1"), the difference between the first Author
and the second author is between the entity class and its alias.

First "Author"
This is the name of the entity class in your Java code. It should match the name of the entity class that is annotated
with @Entity in your JPA/Hibernate setup. This is not a variable but the actual class name and is case-sensitive.

Second "author"
This is an alias that you have defined for instances of the Author entity within the scope of this specific query.
It's a variable-like reference that you use to refer to the elements of the Author class within the query.
The alias can be any valid identifier you choose. It's used to access the properties of the Author entities,
like author.age in the WHERE clause.

In essence, when you write FROM Author author, you're saying, "Select from the Author entity, and refer to each
instance of this entity as author in this query." The alias (second author) is especially useful in more complex
queries, where you might be joining multiple tables and need a clear way to distinguish between different entities.
*/
// --------------------------------------------------------------------------------------------------------------------


