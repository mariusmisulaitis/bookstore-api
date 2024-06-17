package org.marius.bookstoreapi.converters;


import org.marius.bookstoreapi.dto.CreateBookstoreResponse;
import org.marius.bookstoreapi.entities.Bookstore;

public abstract class BookstoreConverter {

    private BookstoreConverter() {
    }


    public static CreateBookstoreResponse convertBookstoreEntityToCreateResponse(Bookstore bookstore) {
        if (bookstore != null) {
            return new CreateBookstoreResponse(bookstore.getId()
                    , bookstore.getName()
                    , bookstore.getAddress()
                    , bookstore.getPhone());
        }
        return null;
    }

}

