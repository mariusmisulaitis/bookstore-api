package org.marius.bookstoreapi.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.sql.Date;

@Data
public class CreateSaleRequest {

    @NotNull(message = "{validation.constraints.not.empty.message}")
    private final Long bookId;

    @NotNull(message = "{validation.constraints.not.empty.message}")
    private final Long bookstoreId;

    @NotNull(message = "{validation.constraints.not.empty.message}")
    @Positive(message = "{validation.constraints.positive.message}")
    @Max(value = 200, message = "{validation.constraints.max.message}")
    private final Integer quantity;

    @NotNull(message = "{validation.constraints.not.empty.message}")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final Date saleDate;

}


