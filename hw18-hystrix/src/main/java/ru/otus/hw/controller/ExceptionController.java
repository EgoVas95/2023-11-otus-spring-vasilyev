package ru.otus.hw.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.otus.hw.exceptions.EntityNotFoundException;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void handleEntityNotFoundEx(Exception ex) {
        log.error(ex.getMessage(), ex);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public void handleEntityNotFoundEx(EntityNotFoundException ex) {
        log.error(ex.getMessage(), ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String[] handleEntityValidateEx(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        return ex.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).toArray(String[]::new);
    }
}
