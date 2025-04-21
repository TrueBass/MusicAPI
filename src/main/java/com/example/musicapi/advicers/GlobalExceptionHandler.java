package com.example.musicapi.advicers;


import com.example.musicapi.exceptions.AlreadyExistsException;
import com.example.musicapi.exceptions.InvalidPasswordException;
import com.example.musicapi.exceptions.NotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<String> handleAlreadyExistsException(AlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<String> handleInvalidPasswordException(InvalidPasswordException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED); // Status 401
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND); // Status 404
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class,
            DataIntegrityViolationException.class
    })
    public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "Something went wrong";
        Object details = null;

        if (ex instanceof MethodArgumentNotValidException validationEx) {
            status = HttpStatus.BAD_REQUEST;
            message = "Validation failed";
            details = validationEx.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            DefaultMessageSourceResolvable::getDefaultMessage,
                            (first, second) -> first
                    ));
        }

        return buildErrorResponse(message, status, details);
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(String message, HttpStatus status, Object details) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        if (details != null) {
            body.put("details", details);
        }
        return new ResponseEntity<>(body, status);
    }
}
