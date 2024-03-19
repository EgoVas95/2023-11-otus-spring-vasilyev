package ru.otus.hw.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String errorMsg) {
        super(errorMsg);
    }
}
