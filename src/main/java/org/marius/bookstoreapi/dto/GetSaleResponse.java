package org.marius.bookstoreapi.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetSaleResponse {
    private Long id;
    private Long bookId;
    private Long bookstoreId;
    private Integer quantity;
    private Date saleDate;
}


