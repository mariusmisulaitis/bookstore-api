package org.marius.bookstoreapi.converters;


import org.marius.bookstoreapi.dto.GetBookResponse;
import org.marius.bookstoreapi.entities.Book;

import java.util.ArrayList;
import java.util.List;


public abstract class BookConverter {

    private BookConverter() {
    }


    public static GetBookResponse convertBookEntityToGetResponse(Book book) {
        GetBookResponse getBookResponse = null;
        if (book != null) {
            getBookResponse = new GetBookResponse();
            getBookResponse.setId(book.getId());
            getBookResponse.setTitle(book.getTitle());
            getBookResponse.setAuthor(book.getAuthor());
            getBookResponse.setIsbn(book.getIsbn());
            getBookResponse.setPrice(book.getPrice());
            getBookResponse.setBookstoreId(book.getBookstore().getId());
        }
        return getBookResponse;
    }


    public static List<GetBookResponse> convertBookEntityListToGetResponse(List<Book> books) {
        List<GetBookResponse> bookResponses = null;
        if (books != null) {
            bookResponses = new ArrayList<>();
            for (Book book : books) {
                bookResponses.add(convertBookEntityToGetResponse(book));
            }
        }
        return bookResponses;
    }
}

