package org.marius.bookstoreapi.exceptions;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Setter
public class ValidationErrorResponse extends BaseErrorResponse {
    private List<String> errors;

    public ValidationErrorResponse(List<FieldError> errors) {
        super("Validation error", HttpStatus.BAD_REQUEST.value());

        this.errors = new ArrayList<>();
        for (FieldError error : errors) {
            this.errors.add(String.format("%s: %s", error.getField(), error.getDefaultMessage()));
        }
    }
}

