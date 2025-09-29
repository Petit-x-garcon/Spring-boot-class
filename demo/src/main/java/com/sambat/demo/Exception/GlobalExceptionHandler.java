package com.sambat.demo.Exception;

import com.sambat.demo.Dto.Base.Response;
import com.sambat.demo.Exception.Model.CustomAuthenticationException;
import com.sambat.demo.Exception.Model.DuplicatedException;
import com.sambat.demo.Exception.Model.NotFoundHandler;
import com.sambat.demo.Exception.Model.UnprocessEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedException.class)
    public ResponseEntity<Response> handleDuplicated(DuplicatedException du){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Response.error("409", "conflict", du.getMessage()));
    }

    @ExceptionHandler(NotFoundHandler.class)
    public ResponseEntity<Response> handleNotFound(NotFoundHandler nf){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Response.error("404", "not found", nf.getMessage()));
    }

    @ExceptionHandler(UnprocessEntityException.class)
    public ResponseEntity<Response> handleUnprocessEntityException(UnprocessEntityException ex){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(Response.error("422", "unprocessable", ex.getMessage()));
    }

    @ExceptionHandler(CustomAuthenticationException.class)
    public ResponseEntity<Response> handleAuthenticationException(CustomAuthenticationException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Response.error("401", "unauthorized", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleGenernalException(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.error("500", "internal error", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();

            errors.put(field, message);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Response.error("400", "invalid", ex.getMessage(), errors));
    }
}
