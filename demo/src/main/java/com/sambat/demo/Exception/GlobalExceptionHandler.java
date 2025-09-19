package com.sambat.demo.Exception;

import com.sambat.demo.Exception.Model.CustomAuthenticationException;
import com.sambat.demo.Exception.Model.DuplicatedException;
import com.sambat.demo.Exception.Model.NotFoundHandler;
import com.sambat.demo.Exception.Model.UnprocessEntityException;
import com.sambat.demo.Model.BaseDataResponseModel;
import com.sambat.demo.Model.BaseResponseModel;
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
    public ResponseEntity<BaseResponseModel> handleDuplicated(DuplicatedException du){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new BaseResponseModel("fail", du.getMessage()));
    }

    @ExceptionHandler(NotFoundHandler.class)
    public ResponseEntity<BaseResponseModel> handleNotFound(NotFoundHandler nf){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponseModel("fail", nf.getMessage()));
    }

    @ExceptionHandler(UnprocessEntityException.class)
    public ResponseEntity<BaseResponseModel> handleUnprocessEntityException(UnprocessEntityException ex){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new BaseResponseModel("fail", ex.getMessage()));
    }

    @ExceptionHandler(CustomAuthenticationException.class)
    public ResponseEntity<BaseResponseModel> handleAuthenticationException(CustomAuthenticationException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new BaseResponseModel("fail", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponseModel> handleGenernalException(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponseModel("fail", "internal server error " + ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseDataResponseModel> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();

            errors.put(field, message);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseDataResponseModel("fail", "validation fail", errors));
    }
}
