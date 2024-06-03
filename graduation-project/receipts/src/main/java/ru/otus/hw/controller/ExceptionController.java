package ru.otus.hw.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.otus.hw.exceptions.EntityNotFoundException;

import java.util.List;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleEntityNotFoundException(EntityNotFoundException ex) {
        log.error("Ошибка поиска записи", ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<String>> handleEntityValidateEx(MethodArgumentNotValidException ex) {
        log.error("Ошибка валидации", ex);
        final String[] errors = ex.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).toArray(String[]::new);
        return ResponseEntity.badRequest()
                .header("errorMsgs", errors)
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
    }
}
