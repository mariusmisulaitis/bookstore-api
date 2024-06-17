package org.marius.bookstoreapi.dto;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateBookRequest {

    @NotBlank(message = "{validation.constraints.not.empty.message}")
    @Size(min = 2, max = 255, message = "{validation.constraints.size.message}")
    private final String title;

    @NotBlank(message = "{validation.constraints.not.empty.message}")
    @Size(min = 2, max = 255, message = "{validation.constraints.size.message}")
    private final String author;

    @NotBlank(message = "{validation.constraints.not.empty.message}")
    @Size(min = 10, max = 20, message = "{validation.constraints.size.message}")
    private final String isbn;

    @NotNull(message = "{validation.constraints.not.empty.message}")
    @Positive(message = "{validation.constraints.positive.message}")
    @Max(value = 200, message = "{validation.constraints.max.message}")
    private final Double price;

    @NotNull(message = "{validation.constraints.not.empty.message}")
    @Positive(message = "{validation.constraints.positive.message}")
    private final Long bookstoreId;
}


