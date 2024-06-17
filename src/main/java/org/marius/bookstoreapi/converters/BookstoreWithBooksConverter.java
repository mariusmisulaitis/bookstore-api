package org.marius.bookstoreapi.converters;


import org.marius.bookstoreapi.dto.CreateBookstoreWithBooksResponse;
import org.marius.bookstoreapi.dto.GetBookResponse;
import org.marius.bookstoreapi.dto.GetBookstoreResponse;
import org.marius.bookstoreapi.entities.Book;
import org.marius.bookstoreapi.entities.Bookstore;

import java.util.ArrayList;
import java.util.List;

public abstract class BookstoreWithBooksConverter {

    private BookstoreWithBooksConverter() {
    }

    public static GetBookstoreResponse convertBookstoreEntityToGetResponse(Bookstore bookstore) {
        GetBookstoreResponse getBookstoreResponse = null;
        if (bookstore != null) {
            getBookstoreResponse = new GetBookstoreResponse();
            getBookstoreResponse.setId(bookstore.getId());
            getBookstoreResponse.setName(bookstore.getName());
            getBookstoreResponse.setAddress(bookstore.getAddress());
            getBookstoreResponse.setPhone(bookstore.getPhone());
        }
        return getBookstoreResponse;
    }


    public static List<GetBookstoreResponse> convertBookstoreEntityListToGetResponse(List<Bookstore> bookstores) {
        List<GetBookstoreResponse> bookstoreResponses = null;
        if (bookstores != null) {
            bookstoreResponses = new ArrayList<>();
            for (Bookstore bookstore : bookstores) {
                bookstoreResponses.add(convertBookstoreEntityToGetResponse(bookstore));
            }
        }
        return bookstoreResponses;
    }

    public static CreateBookstoreWithBooksResponse convertBookstoreWithBooksToCreateBookResponse(Bookstore bookstore) {
        CreateBookstoreWithBooksResponse createBookstoreWithBooksResponse = null;
        if (bookstore != null) {
            createBookstoreWithBooksResponse = new CreateBookstoreWithBooksResponse();
            createBookstoreWithBooksResponse.setId(bookstore.getId());
            createBookstoreWithBooksResponse.setName(bookstore.getName());
            createBookstoreWithBooksResponse.setAddress(bookstore.getAddress());
            createBookstoreWithBooksResponse.setPhone(bookstore.getPhone());

            List<GetBookResponse> bookResponses = new ArrayList<>();
            for (Book book : bookstore.getBooks()) {
                bookResponses.add(BookConverter.convertBookEntityToGetResponse(book));
            }
            createBookstoreWithBooksResponse.setBooks(bookResponses);
        }
        return createBookstoreWithBooksResponse;
    }

}


