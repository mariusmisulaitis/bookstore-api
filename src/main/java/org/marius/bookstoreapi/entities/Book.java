package org.marius.bookstoreapi.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String author;

    private String isbn;

    private Double price;

    @ManyToOne
    @JoinColumn(name = "bookstore_id")
    private Bookstore bookstore;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
    private List<Sale> sales;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

}


