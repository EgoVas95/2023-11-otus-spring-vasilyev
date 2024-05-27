package ru.otus.hw.exceptions;

public class EntityDeleteException extends RuntimeException {
    public EntityDeleteException(String message) {
        super(message);
    }
}
