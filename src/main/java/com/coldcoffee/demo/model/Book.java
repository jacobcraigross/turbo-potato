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
@Entity
@Table(name = "books")
public class Book {
    @Id
    private String isbn;

    private String title;

    @ManyToOne(cascade = CascadeType.ALL) // --> One author can write many books. 
    @JoinColumn(name = "author_id")
    private Author author;
}
