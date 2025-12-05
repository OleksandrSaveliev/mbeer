package com.tmdna.mbeer.exception;

import com.tmdna.mbeer.dto.ErrorResponse;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException exception, WebRequest request) {

        List<String> details = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(exception.getStatusCode().value())
                .message("Invalid request parameters")
                .error("Validation Error")
                .details(details)
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
