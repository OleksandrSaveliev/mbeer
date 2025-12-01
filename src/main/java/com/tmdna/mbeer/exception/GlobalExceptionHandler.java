package com.tmdna.mbeer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
//
//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<String> handleBeerNotFound(NotFoundException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleGenericException(Exception ex) {
//        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
