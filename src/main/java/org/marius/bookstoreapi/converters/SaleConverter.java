package org.marius.bookstoreapi.converters;


import org.marius.bookstoreapi.dto.GetSaleResponse;
import org.marius.bookstoreapi.entities.Sale;

import java.util.ArrayList;
import java.util.List;

public abstract class SaleConverter {

    private SaleConverter() {
    }

    public static GetSaleResponse convertSaleEntityToGetResponse(Sale sale) {
        GetSaleResponse getSaleResponse = null;
        if (sale != null) {
            getSaleResponse = new GetSaleResponse();
            getSaleResponse.setId(sale.getId());
            getSaleResponse.setBookId(sale.getBook().getId());
            getSaleResponse.setBookstoreId(sale.getBookstore().getId());
            getSaleResponse.setQuantity(sale.getQuantity());
            getSaleResponse.setSaleDate(sale.getSaleDate());
        }
        return getSaleResponse;
    }


    public static List<GetSaleResponse> convertSaleEntityListToGetResponse(List<Sale> sales) {
        List<GetSaleResponse> saleResponses = null;
        if (sales != null) {
            saleResponses = new ArrayList<>();
            for (Sale sale : sales) {
                saleResponses.add(convertSaleEntityToGetResponse(sale));
            }
        }
        return saleResponses;
    }
}


