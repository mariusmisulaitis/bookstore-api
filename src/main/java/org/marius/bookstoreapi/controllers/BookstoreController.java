package org.marius.bookstoreapi.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.marius.bookstoreapi.converters.BookConverter;
import org.marius.bookstoreapi.converters.BookstoreConverter;
import org.marius.bookstoreapi.converters.BookstoreWithBooksConverter;
import org.marius.bookstoreapi.dto.*;
import org.marius.bookstoreapi.entities.Book;
import org.marius.bookstoreapi.entities.Bookstore;
import org.marius.bookstoreapi.exceptions.NotFoundErrorResponse;
import org.marius.bookstoreapi.exceptions.ValidationErrorResponse;
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
@RequestMapping("/bookstores")
public class BookstoreController {

    private final BookstoreService bookstoreService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    @GetMapping
    public ResponseEntity<List<GetBookstoreResponse>> getAllBookstores(@RequestParam(required = false) String name) {
        log.info("Get all bookstores");
        List<Bookstore> bookstores;

        if (name != null && !name.isEmpty()) {
            bookstores = this.bookstoreService.getBookstoresByName(name);
        } else {
            bookstores = this.bookstoreService.getAllBookstores();
        }

        if (bookstores.isEmpty()) {
            log.info("No bookstores found");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        log.info("Found {} bookstores", bookstores.size());
        ResponseEntity<List<GetBookstoreResponse>> response = ResponseEntity.status(HttpStatus.OK)
                .body(BookstoreWithBooksConverter.convertBookstoreEntityListToGetResponse(bookstores));
        log.debug("Response: {}", response);
        return response;
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping
    public ResponseEntity<?> addBookstore(@Valid @RequestBody CreateBookstoreRequest createBookstoreRequest,
                                          BindingResult bindingResult) {
        log.debug("Add bookstore request: {}", createBookstoreRequest);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(bindingResult.getFieldErrors()));
        }

        Bookstore result = this.bookstoreService.addBookstore(createBookstoreRequest);
        log.info("Bookstore created");
        ResponseEntity<?> response = ResponseEntity.status(HttpStatus.CREATED)
                .body(BookstoreConverter.convertBookstoreEntityToCreateResponse(result));
        log.info("Response: {}", createBookstoreRequest);
        return response;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    @GetMapping("/{bookstoreId}")
    public ResponseEntity<?> getBookstoreById(@PathVariable Long bookstoreId) {
        log.debug("Get bookstore by id: {}", bookstoreId);
        Bookstore bookstore = this.bookstoreService.getBookstoreById(bookstoreId);
        if (bookstore == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("bookstore", "id", bookstoreId.toString()));
        }
        ResponseEntity<GetBookstoreResponse> response = ResponseEntity.ok(
                BookstoreWithBooksConverter.convertBookstoreEntityToGetResponse(bookstore));
        log.debug("Response: {}", response);
        return response;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PatchMapping("/{bookstoreId}/title")
    public ResponseEntity<?> patchBookstoreByIdTitle(@PathVariable Long bookstoreId,
                                                     @RequestBody @NotBlank @Size(min = 2, max = 50) String newName,
                                                     BindingResult bindingResult) {
        log.debug("Patch bookstore {} name: {}", bookstoreId, newName);


        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(bindingResult.getFieldErrors()));
        }


        Bookstore bookstore = this.bookstoreService.patchBookstoreByIdName(bookstoreId, newName);
        if (bookstore == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("bookstore", "id", bookstoreId.toString()));
        }

        ResponseEntity<GetBookstoreResponse> response = ResponseEntity.ok(
                BookstoreWithBooksConverter.convertBookstoreEntityToGetResponse(bookstore));
        log.debug("Response: {}", response);
        return response;
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/{bookstoreId}")
    public ResponseEntity<?> updateBookstoreById(@PathVariable Long bookstoreId,
                                                 @Valid @RequestBody CreateBookstoreRequest createBookstoreRequest,
                                                 BindingResult bindingResult) {
        log.debug("Update bookstore {}: {}", bookstoreId, createBookstoreRequest);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(bindingResult.getFieldErrors()));
        }

        Bookstore bookstore = this.bookstoreService.updateBookstoreById(bookstoreId, createBookstoreRequest);
        if (bookstore == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("bookstore", "id", bookstoreId.toString()));
        }

        ResponseEntity<GetBookstoreResponse> response = ResponseEntity.ok(
                BookstoreWithBooksConverter.convertBookstoreEntityToGetResponse(bookstore));
        log.debug("Response: {}", response);
        return response;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping("/{bookstoreId}")
    public ResponseEntity<?> deleteBookstoreById(@PathVariable Long bookstoreId) {
        log.debug("Delete bookstore by id: {}", bookstoreId);
        boolean deleted = this.bookstoreService.deleteBookstoreById(bookstoreId);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("bookstore", "id", bookstoreId.toString()));
        }
        log.info("Bookstore with id {} deleted successfully", bookstoreId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    @GetMapping("/{bookstoreId}/books")
    public ResponseEntity<?> getBooksByBookstoreId(@PathVariable Long bookstoreId) {
        log.debug("Get books by bookstore id {}", bookstoreId);
        List<Book> books = this.bookstoreService.getAllBooksByBookstoreId(bookstoreId);
        if (books == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("books", "bookstore id", bookstoreId.toString()));
        }

        if (books.isEmpty()) {
            log.info("No books found at bookstore {}", bookstoreId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        ResponseEntity<List<GetBookResponse>> response = ResponseEntity.ok(
                BookConverter.convertBookEntityListToGetResponse(books));
        log.debug("Response: {}", response);
        return response;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("/{bookstoreId}/books")
    public ResponseEntity<?> addBookToBookstore(@PathVariable Long bookstoreId,
                                                @Valid @RequestBody CreateBookRequest createBookRequest,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(bindingResult.getFieldErrors()));
        }

        Bookstore bookstore = this.bookstoreService.addBookToBookstoreByBookstoreId(bookstoreId, createBookRequest);
        if (bookstore == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("bookstore", "id", bookstoreId.toString()));
        }

        ResponseEntity<CreateBookstoreWithBooksResponse> response = ResponseEntity.status(HttpStatus.CREATED)
                .body(BookstoreWithBooksConverter.convertBookstoreWithBooksToCreateBookResponse(bookstore));
        log.debug("Response: {}", response);
        return response;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    @GetMapping("/{bookstoreId}/books/{bookId}")
    public ResponseEntity<?> getBookByBookstoreIdAndBookId(@PathVariable Long bookstoreId,
                                                           @PathVariable Long bookId) {
        log.debug("Get book from bookstore {} by book id {}", bookstoreId, bookId);
        Book book = this.bookstoreService.getBookByBookstoreIdAndBookId(bookstoreId, bookId);

        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("bookstore or book"
                            , "bookstoreId|bookId"
                            , bookstoreId.toString() + "|" + bookId.toString()));
        }

        ResponseEntity<GetBookResponse> response = ResponseEntity.ok(
                BookConverter.convertBookEntityToGetResponse(book));
        log.debug("Response: {}", response);
        return response;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping("/{bookstoreId}/books/{bookId}")
    public ResponseEntity<?> deleteBookByBookstoreIdAndBookId(@PathVariable Long bookstoreId,
                                                              @PathVariable Long bookId) {
        log.debug("Delete book from bookstore {} by book id {}", bookstoreId, bookId);
        boolean deleted = this.bookstoreService.deleteBookByBookstoreIdAndBookId(bookstoreId, bookId);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("bookstore or book"
                            , "bookstoreId|bookId"
                            , bookstoreId.toString() + "|" + bookId.toString()));
        }
        return ResponseEntity.noContent().build();
    }
}


