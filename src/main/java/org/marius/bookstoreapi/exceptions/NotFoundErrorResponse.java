package org.marius.bookstoreapi.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@Getter
@Setter
public class NotFoundErrorResponse extends BaseErrorResponse {
    public NotFoundErrorResponse(String entityName, String criteriaName, String criteriaValue) {
        super(HttpStatus.NOT_FOUND.value(),
                String.format("Entity %s not found by %s: %s", entityName, criteriaName, criteriaValue));
    }
}

