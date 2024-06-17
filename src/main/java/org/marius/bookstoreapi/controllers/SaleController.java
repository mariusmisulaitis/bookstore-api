package org.marius.bookstoreapi.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.marius.bookstoreapi.converters.SaleConverter;
import org.marius.bookstoreapi.dto.CreateSaleRequest;
import org.marius.bookstoreapi.dto.GetSaleResponse;
import org.marius.bookstoreapi.entities.Sale;
import org.marius.bookstoreapi.exceptions.NotFoundErrorResponse;
import org.marius.bookstoreapi.exceptions.ValidationErrorResponse;
import org.marius.bookstoreapi.services.BookService;
import org.marius.bookstoreapi.services.BookstoreService;
import org.marius.bookstoreapi.services.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sales")
public class SaleController {

    private final SaleService saleService;
    private final BookService bookService;
    private final BookstoreService bookstoreService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    @GetMapping
    public ResponseEntity<List<GetSaleResponse>> getAllSales() {
        log.debug("Get all sales");
        List<Sale> sales = this.saleService.getAllSales();

        if (sales.isEmpty()) {
            log.info("No sales found");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        log.info("Found {} sales", sales.size());
        ResponseEntity<List<GetSaleResponse>> response = ResponseEntity.status(HttpStatus.OK)
                .body(SaleConverter.convertSaleEntityListToGetResponse(sales));
        log.debug("Response: {}", response);
        return response;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    @GetMapping("/{saleId}")
    public ResponseEntity<?> getSaleById(@PathVariable Long saleId) {
        log.debug("Get sale by id: {}", saleId);
        Sale sale = this.saleService.getSaleById(saleId);
        if (sale == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("sale", "id", saleId.toString()));
        }
        ResponseEntity<GetSaleResponse> response = ResponseEntity.ok(
                SaleConverter.convertSaleEntityToGetResponse(sale));
        log.debug("Response: {}", response);
        return response;
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping
    public ResponseEntity<?> addSale(@Valid @RequestBody CreateSaleRequest createSaleRequest,
                                     BindingResult bindingResult) {
        log.debug("Add sale request: {}", createSaleRequest);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(bindingResult.getFieldErrors()));
        }

        if (!this.bookService.existsById(createSaleRequest.getBookId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("book", "id", createSaleRequest.getBookId().toString()));
        }
        if (!this.bookstoreService.existsById(createSaleRequest.getBookstoreId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("bookstore", "id", createSaleRequest.getBookstoreId().toString()));
        }

        Sale result = this.saleService.addSale(createSaleRequest);
        log.info("Sale created");
        ResponseEntity<?> response = ResponseEntity.status(HttpStatus.CREATED)
                .body(SaleConverter.convertSaleEntityToGetResponse(result));
        log.debug("Response: {}", response);
        return response;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/{saleId}")
    public ResponseEntity<?> updateSaleById(@PathVariable Long saleId,
                                            @Valid @RequestBody CreateSaleRequest updateRequest,
                                            BindingResult bindingResult) {
        log.debug("Update sale {} request: {}", saleId, updateRequest);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(bindingResult.getFieldErrors()));
        }

        if (!this.bookService.existsById(updateRequest.getBookId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("book", "id", updateRequest.getBookId().toString()));
        }
        if (!this.bookstoreService.existsById(updateRequest.getBookstoreId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("bookstore", "id", updateRequest.getBookstoreId().toString()));
        }

        Sale updatedSale = this.saleService.updateSaleById(saleId, updateRequest);
        if (updatedSale == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("sale", "id", saleId.toString()));
        }

        ResponseEntity<GetSaleResponse> response = ResponseEntity.ok(
                SaleConverter.convertSaleEntityToGetResponse(updatedSale));
        log.debug("Response: {}", response);
        return response;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping("/{saleId}")
    public ResponseEntity<?> deleteSaleById(@PathVariable Long saleId) {
        log.debug("Delete sale by id: {}", saleId);
        boolean deleted = this.saleService.deleteSaleById(saleId);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundErrorResponse("sale", "id", saleId.toString()));
        }
        log.info("Sale with id {} deleted successfully", saleId);
        return ResponseEntity.noContent().build();
    }
}


