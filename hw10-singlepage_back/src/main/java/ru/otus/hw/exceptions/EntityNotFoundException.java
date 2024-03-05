package ru.otus.hw.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(String errorMsg) {
        super(errorMsg);
    }
}
