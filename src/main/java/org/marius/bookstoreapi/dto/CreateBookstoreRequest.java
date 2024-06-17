package org.marius.bookstoreapi.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateBookstoreRequest {

    @NotBlank(message = "{validation.constraints.not.empty.message}")
    @Size(min = 2, max = 50, message = "{validation.constraints.size.message}")
    private final String name;

    @NotBlank(message = "{validation.constraints.not.empty.message}")
    @Size(min = 2, max = 255, message = "{validation.constraints.size.message}")
    private final String address;

    @NotBlank(message = "{validation.constraints.not.empty.message}")
    @Size(min = 2, max = 50, message = "{validation.constraints.size.message}")
    private final String phone;
}


