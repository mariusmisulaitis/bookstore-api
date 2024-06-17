package org.marius.bookstoreapi.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.marius.bookstoreapi.dto.CreateSaleRequest;
import org.marius.bookstoreapi.entities.Book;
import org.marius.bookstoreapi.entities.Bookstore;
import org.marius.bookstoreapi.entities.Sale;
import org.marius.bookstoreapi.repositories.BookRepository;
import org.marius.bookstoreapi.repositories.BookstoreRepository;
import org.marius.bookstoreapi.repositories.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final BookRepository bookRepository;
    private final BookstoreRepository bookstoreRepository;

    public List<Sale> getAllSales() {
        return this.saleRepository.findAll();
    }

    public Sale getSaleById(Long saleId) {
        Sale result = this.saleRepository.findById(saleId).orElse(null);
        if (result == null) {
            log.warn("Sale with id {} not found", saleId);
        }
        return result;
    }

    @Transactional
    public Sale addSale(CreateSaleRequest createSaleRequest) {
        Sale sale = new Sale();
        sale.setQuantity(createSaleRequest.getQuantity());
        sale.setSaleDate(createSaleRequest.getSaleDate());

        Book book = this.bookRepository.findById(createSaleRequest.getBookId()).orElse(null);
        if (book == null) {
            log.warn("Book with id {} not found", createSaleRequest.getBookId());
            return null;
        }
        sale.setBook(book);

        Bookstore bookstore = this.bookstoreRepository.findById(createSaleRequest.getBookstoreId()).orElse(null);
        if (bookstore == null) {
            log.warn("Bookstore with id {} not found", createSaleRequest.getBookstoreId());
            return null;
        }
        sale.setBookstore(bookstore);

        return this.saleRepository.saveAndFlush(sale);
    }


    @Transactional
    public Sale updateSaleById(Long id, CreateSaleRequest createSaleRequest) {
        Sale sale = getSaleById(id);
        if (sale == null) {
            return null;
        }

        Book book = bookRepository.findById(createSaleRequest.getBookId()).orElse(null);
        if (book == null) {
            log.warn("Book with id {} not found", createSaleRequest.getBookId());
            return null;
        }

        Bookstore bookstore = bookstoreRepository.findById(createSaleRequest.getBookstoreId()).orElse(null);
        if (bookstore == null) {
            log.warn("Bookstore with id {} not found", createSaleRequest.getBookstoreId());
            return null;
        }

        sale.setQuantity(createSaleRequest.getQuantity());
        sale.setSaleDate(createSaleRequest.getSaleDate());
        sale.setBook(book);
        sale.setBookstore(bookstore);

        return saleRepository.saveAndFlush(sale);
    }

    @Transactional
    public boolean deleteSaleById(Long id) {
        Sale sale = getSaleById(id);
        if (sale == null) {
            return false;
        }

        saleRepository.delete(sale);
        return true;
    }

}


