package org.marius.bookstoreapi.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.marius.bookstoreapi.dto.CreateBookRequest;
import org.marius.bookstoreapi.dto.CreateBookstoreRequest;
import org.marius.bookstoreapi.entities.Book;
import org.marius.bookstoreapi.entities.Bookstore;
import org.marius.bookstoreapi.repositories.BookRepository;
import org.marius.bookstoreapi.repositories.BookstoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class BookstoreService {

    private final BookstoreRepository bookstoreRepository;
    private final BookRepository bookRepository;

    public List<Bookstore> getAllBookstores() {
        return this.bookstoreRepository.findAll();
    }

    public List<Bookstore> getBookstoresByName(String name) {
        return bookstoreRepository.findByNameContainingIgnoreCase(name);
    }

    public Bookstore getBookstoreById(Long bookstoreId) {
        Bookstore result = this.bookstoreRepository.findById(bookstoreId).orElse(null);
        if (result == null) {
            log.warn("Bookstore with id {} not found", bookstoreId);
        }
        return result;
    }

    public List<Book> getAllBooksByBookstoreId(Long bookstoreId) {
        Bookstore bookstore = getBookstoreById(bookstoreId);
        if (bookstore == null) {
            return null;
        }
        if (bookstore.getBooks().isEmpty()) {
            log.trace("Book list is empty");
        }
        return bookstore.getBooks();
    }

    public Book getBookByBookstoreIdAndBookId(Long bookstoreId, Long bookId) {
        Bookstore bookstore = getBookstoreById(bookstoreId);
        if (bookstore == null) {
            return null;
        }
        Book result = bookstore.getBooks().stream()
                .filter(book -> book.getId().equals(bookId))
                .findFirst()
                .orElse(null);
        if (result == null) {
            log.warn("Book with id {} not found in bookstore {}", bookId, bookstoreId);
        }
        return result;
    }

    public boolean existsById(Long bookstoreId) {
        return bookstoreRepository.existsById(bookstoreId);
    }

    @Transactional
    public Bookstore addBookstore(CreateBookstoreRequest createBookstoreRequest) {
        Bookstore bookstore = new Bookstore();
        bookstore.setName(createBookstoreRequest.getName());
        bookstore.setAddress(createBookstoreRequest.getAddress());
        bookstore.setPhone(createBookstoreRequest.getPhone());
        bookstore.setBooks(new ArrayList<>());
        return this.bookstoreRepository.saveAndFlush(bookstore);
    }

    @Transactional
    public Bookstore addBookToBookstoreByBookstoreId(Long bookstoreId, CreateBookRequest createBookRequest) {
        Bookstore bookstore = getBookstoreById(bookstoreId);
        if (bookstore != null) {
            Book book = new Book();
            book.setTitle(createBookRequest.getTitle());
            book.setAuthor(createBookRequest.getAuthor());
            book.setIsbn(createBookRequest.getIsbn());
            book.setPrice(createBookRequest.getPrice());
            book.setBookstore(bookstore);

            bookstore.getBooks().add(book);
            return this.bookstoreRepository.saveAndFlush(bookstore);
        }
        return bookstore;
    }

    @Transactional
    public Bookstore patchBookstoreByIdName(Long id, String newName) {
        Bookstore bookstore = getBookstoreById(id);
        if (bookstore == null) {
            return null;
        }
        bookstore.setName(newName);
        return this.bookstoreRepository.saveAndFlush(bookstore);
    }

    @Transactional
    public Bookstore updateBookstoreById(Long id, CreateBookstoreRequest createBookstoreRequest) {
        Bookstore bookstore = getBookstoreById(id);
        if (bookstore != null) {
            bookstore.setName(createBookstoreRequest.getName());
            bookstore.setAddress(createBookstoreRequest.getAddress());
            bookstore.setPhone(createBookstoreRequest.getPhone());
            return bookstoreRepository.saveAndFlush(bookstore);
        }
        return null;
    }

    @Transactional
    public boolean deleteBookstoreById(Long id) {
        Bookstore bookstore = getBookstoreById(id);
        if (bookstore != null) {
            bookstoreRepository.delete(bookstore);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deleteBookByBookstoreIdAndBookId(Long bookstoreId, Long bookId) {
        Bookstore bookstore = getBookstoreById(bookstoreId);
        if (bookstore == null) {
            log.warn("Bookstore with id {} not found", bookstoreId);
            return false;
        }

        Book bookToDelete = bookstore.getBooks().stream()
                .filter(book -> book.getId().equals(bookId))
                .findFirst()
                .orElse(null);

        if (bookToDelete == null) {
            log.warn("Book with id {} not found in bookstore {}", bookId, bookstoreId);
            return false;
        }

        bookstore.getBooks().remove(bookToDelete);
        bookRepository.delete(bookToDelete);

        bookstoreRepository.saveAndFlush(bookstore);

        return true;
    }
}


