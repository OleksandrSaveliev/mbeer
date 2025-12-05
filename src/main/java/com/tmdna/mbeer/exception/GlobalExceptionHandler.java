package com.tmdna.mbeer.exception;

import com.tmdna.mbeer.dto.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ErrorResponse> handleTransactionSystemException(TransactionSystemException exception, WebRequest request) {

        String errorMessage = "Database transaction error";
        List<String> details = new ArrayList<>();
        Throwable rootCause = exception.getRootCause();
        if (rootCause instanceof ConstraintViolationException ex) {
            errorMessage = "Database constraint violation";
            details = ex.getConstraintViolations().stream()
                    .map(violation -> violation.getPropertyPath().toString() + ": " + violation.getMessage())
                    .toList();
        } else {
            details.add(rootCause != null ? rootCause.getMessage() : "Unknown database error");
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(errorMessage)
                .error("Transaction Error")
                .details(details.isEmpty() ? null : details)
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

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
