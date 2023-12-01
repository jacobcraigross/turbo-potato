package com.coldcoffee.demo.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity // ---> JPA
@Table(name = "authors") // ---> JPA
public class Author {
    @Id // ---> JPA
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_seq") // ---> JPA
    private Long id;
    private String name;
    private Integer age;
}

// Before w/ the JDBC commit, this class was a POJO, now we added the JPA annotations and it becomes an Entity
