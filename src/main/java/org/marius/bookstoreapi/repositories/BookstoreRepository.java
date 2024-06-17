package org.marius.bookstoreapi.repositories;

import org.marius.bookstoreapi.entities.Bookstore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookstoreRepository extends JpaRepository<Bookstore, Long> {

    List<Bookstore> findByNameContainingIgnoreCase(String name);

}


