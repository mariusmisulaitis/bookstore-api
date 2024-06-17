package org.marius.bookstoreapi.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.marius.bookstoreapi.converters.BookConverter;
import org.marius.bookstoreapi.dto.CreateBookRequest;
import org.marius.bookstoreapi.dto.GetBookResponse;
import org.marius.bookstoreapi.entities.Book;
import org.marius.bookstoreapi.entities.Bookstore;
import org.marius.bookstoreapi.exceptions.NotFoundErrorResponse;
import org.marius.bookstoreapi.exceptions.ValidationErrorResponse;
import org.marius.bookstoreapi.services.BookService;
import org.marius.bookstoreapi.services.BookstoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final BookstoreService bookstoreService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    @GetMapping
    public ResponseEntity<List<GetBookResponse>> getAllBooks(@RequestParam(required = false) String title) {
        log.info("Get all books");
        List<Book> books;

        if (title != null && !title.isEmpty()) {
            books = this.bookService.getBooksByTitle(title);
        } else {
            books = this.bookService.getAllBooks();
        }

        if (books.isEmpty()) {
            log.info("No books found");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        log.info("Found {} books", books.size());
        ResponseEntity<List<GetBookResponse>> response = ResponseEntity.status(HttpStatus.OK)
                .body(BookConverter.convertBookEntityListToGetResponse(books));
        log.debug("Response: {}", response);
        return response;
    }


    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    @GetMapping("/{bookId}")
    public ResponseEntity<?> getBookById(@PathVariable Long bookId) {
        log.debug("Get book by id: {}", bookId);
        Book book = this.bookService.getBookById(bookId);
        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("book", "id", bookId.toString()));
        }
        ResponseEntity<GetBookResponse> response = ResponseEntity.ok(
                BookConverter.convertBookEntityToGetResponse(book));
        log.debug("Response: {}", response);
        return response;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping
    public ResponseEntity<?> addBook(@Valid @RequestBody CreateBookRequest createBookRequest,
                                     BindingResult bindingResult) {
        log.info("Creating a new book");
        if (bindingResult.hasErrors()) {
            log.warn("createBook request has errors");
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(bindingResult.getFieldErrors()));
        }

        Bookstore bookstore = this.bookstoreService.getBookstoreById(createBookRequest.getBookstoreId());
        if (bookstore == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("bookstore", "id"
                            , createBookRequest.getBookstoreId().toString()));
        }

        Book result = this.bookService.addBook(createBookRequest);
        ResponseEntity<?> response = ResponseEntity.status(HttpStatus.CREATED)
                .body(BookConverter.convertBookEntityToGetResponse(result));
        log.info("Book created");
        log.debug("Response: {}", response);
        return response;
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/{bookId}")
    public ResponseEntity<?> updateBookById(@PathVariable Long bookId,
                                            @Valid @RequestBody CreateBookRequest updateRequest,
                                            BindingResult bindingResult) {
        log.debug("Update book {} request: {}", bookId, updateRequest);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(bindingResult.getFieldErrors()));
        }

        Bookstore bookstore = this.bookstoreService.getBookstoreById(updateRequest.getBookstoreId());
        if (bookstore == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("bookstore", "id"
                            , updateRequest.getBookstoreId().toString()));
        }

        Book updatedBook = this.bookService.updateBookById(bookId, updateRequest);
        if (updatedBook == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("book", "id", bookId.toString()));
        }

        ResponseEntity<GetBookResponse> response = ResponseEntity.ok(
                BookConverter.convertBookEntityToGetResponse(updatedBook));
        log.info("Response: {}", response);
        return response;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteBookById(@PathVariable Long bookId) {
        log.info("Delete book by id: {}", bookId);
        boolean deleted = this.bookService.deleteBookById(bookId);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("book", "id", bookId.toString()));
        }
        log.info("Book with id {} deleted successfully", bookId);
        return ResponseEntity.noContent().build();
    }
}


