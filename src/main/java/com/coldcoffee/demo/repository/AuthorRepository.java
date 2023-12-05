package com.coldcoffee.demo.repository;
import com.coldcoffee.demo.model.AuthorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {
    Iterable<AuthorEntity> ageLessThan(int age);

    @Query("SELECT author FROM AuthorEntity author WHERE author.age > ?1")
    Iterable<AuthorEntity> findAuthorsWithAgeGreaterThan(int age);
}







// --------------------------------------------------------------------------------------------------------------------
/*
In the query @Query("SELECT authorEntity FROM AuthorEntity authorEntity WHERE authorEntity.age > ?1"), the difference between the first AuthorEntity
and the second authorEntity is between the entity class and its alias.

First "AuthorEntity"
This is the name of the entity class in your Java code. It should match the name of the entity class that is annotated
with @Entity in your JPA/Hibernate setup. This is not a variable but the actual class name and is case-sensitive.

Second "authorEntity"
This is an alias that you have defined for instances of the AuthorEntity entity within the scope of this specific query.
It's a variable-like reference that you use to refer to the elements of the AuthorEntity class within the query.
The alias can be any valid identifier you choose. It's used to access the properties of the AuthorEntity entities,
like authorEntity.age in the WHERE clause.

In essence, when you write FROM AuthorEntity authorEntity, you're saying, "Select from the AuthorEntity entity, and refer to each
instance of this entity as authorEntity in this query." The alias (second authorEntity) is especially useful in more complex
queries, where you might be joining multiple tables and need a clear way to distinguish between different entities.
*/
// --------------------------------------------------------------------------------------------------------------------


