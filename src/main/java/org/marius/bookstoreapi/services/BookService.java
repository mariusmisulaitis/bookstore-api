package org.marius.bookstoreapi.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.marius.bookstoreapi.dto.CreateBookRequest;
import org.marius.bookstoreapi.entities.Book;
import org.marius.bookstoreapi.entities.Bookstore;
import org.marius.bookstoreapi.repositories.BookRepository;
import org.marius.bookstoreapi.repositories.BookstoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookstoreRepository bookstoreRepository;

    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    public List<Book> getBooksByTitle(String name) {
        return bookRepository.findByTitleContainingIgnoreCase(name);
    }

    public Book getBookById(Long bookId) {
        Book result = this.bookRepository.findById(bookId).orElse(null);
        if (result == null) {
            log.warn("Book with id {} not found", bookId);
        }
        return result;
    }

    public boolean existsById(Long bookId) {
        return bookRepository.existsById(bookId);
    }

    @Transactional
    public Book addBook(CreateBookRequest createBookRequest) {
        Book book = new Book();
        book.setTitle(createBookRequest.getTitle());
        book.setAuthor(createBookRequest.getAuthor());
        book.setIsbn(createBookRequest.getIsbn());
        book.setPrice(createBookRequest.getPrice());

        Bookstore bookstore = this.bookstoreRepository.findById(createBookRequest.getBookstoreId()).orElse(null);
        if (bookstore == null) {
            log.warn("Bookstore with id {} not found", createBookRequest.getBookstoreId());
            return null;
        }
        book.setBookstore(bookstore);

        return this.bookRepository.saveAndFlush(book);
    }

    @Transactional
    public Book updateBookById(Long id, CreateBookRequest createBookRequest) {
        Book book = getBookById(id);
        if (book == null) {
            return null;
        }

        book.setTitle(createBookRequest.getTitle());
        book.setAuthor(createBookRequest.getAuthor());
        book.setIsbn(createBookRequest.getIsbn());
        book.setPrice(createBookRequest.getPrice());

        return bookRepository.saveAndFlush(book);
    }

    @Transactional
    public boolean deleteBookById(Long id) {
        Book book = getBookById(id);
        if (book == null) {
            return false;
        }

        bookRepository.delete(book);
        return true;
    }
}


