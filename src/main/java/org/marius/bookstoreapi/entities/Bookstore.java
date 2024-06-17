package org.marius.bookstoreapi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Bookstore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private String phone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bookstore")
    private List<Book> books;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bookstore")
    private List<Sale> sales;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

}





