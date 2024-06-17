package org.marius.bookstoreapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetBookResponse {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Double price;
    private Long bookstoreId;
}


