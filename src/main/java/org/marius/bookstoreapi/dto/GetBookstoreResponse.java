package org.marius.bookstoreapi.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetBookstoreResponse {
    private Long id;
    private String name;
    private String address;
    private String phone;
}


