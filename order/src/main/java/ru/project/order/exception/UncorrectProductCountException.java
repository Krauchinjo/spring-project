package ru.project.order.exception;

public class UncorrectProductCountException extends RuntimeException {
    public UncorrectProductCountException(String message) {
        super(message);
    }
}
