package org.marius.bookstoreapi.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateBookstoreResponse {
    private Long id;
    private String name;
    private String address;
    private String phone;

    public CreateBookstoreResponse(Long id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

}

