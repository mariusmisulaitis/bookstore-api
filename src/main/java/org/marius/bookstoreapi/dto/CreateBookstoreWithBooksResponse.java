package org.marius.bookstoreapi.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class CreateBookstoreWithBooksResponse {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private List<GetBookResponse> books;

}



