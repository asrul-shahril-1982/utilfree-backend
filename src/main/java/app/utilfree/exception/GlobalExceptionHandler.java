package app.utilfree.exception;

import app.utilfree.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles @Valid validation failures — returns field-level error messages.
     * Example: missing required field, invalid email format.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field   = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            fieldErrors.put(field, message);
        });

        return ResponseEntity.badRequest().body(Map.of(
            "success",   false,
            "status",    400,
            "message",   "Validation failed",
            "errors",    fieldErrors,
            "timestamp", LocalDateTime.now().toString()
        ));
    }

    /**
     * Handles any unexpected runtime exceptions.
     * Returns 500 with a safe, non-leaking message.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
            "success",   false,
            "status",    500,
            "message",   "An unexpected error occurred. Please try again.",
            "timestamp", LocalDateTime.now().toString()
        ));
    }

    /**
     * Handles resource not found (e.g. invoice ID doesn't exist).
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
            "success",   false,
            "status",    404,
            "message",   ex.getMessage(),
            "timestamp", LocalDateTime.now().toString()
        ));
    }
}
