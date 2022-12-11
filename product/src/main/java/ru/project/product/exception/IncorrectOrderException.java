package ru.project.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "IncorrectOrder")
public class IncorrectOrderException extends RuntimeException {
    public IncorrectOrderException(String message) {
        super(message);
    }
}
