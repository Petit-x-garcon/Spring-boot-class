package com.sambat.demo.Exception;

import com.sambat.demo.Exception.Model.DuplicatedException;
import com.sambat.demo.Exception.Model.NotFoundHandler;
import com.sambat.demo.Model.BaseResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponseModel> handleInternal(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponseModel("fail", "internal server error" + ex.getMessage()));
    }
}
