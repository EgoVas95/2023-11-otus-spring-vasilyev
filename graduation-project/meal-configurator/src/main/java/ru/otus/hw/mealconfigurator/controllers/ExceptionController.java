package ru.otus.hw.mealconfigurator.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleEntityValidateEx(MethodArgumentNotValidException ex) {
        log.error("Ошибка валидации", ex);
        final String[] errors = ex.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).toArray(String[]::new);
        return ResponseEntity.badRequest()
                .header("errorMsgs", errors)
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleEntityValidateEx(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.badRequest().build();
    }
}

