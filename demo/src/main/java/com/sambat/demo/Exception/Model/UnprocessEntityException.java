package com.sambat.demo.Exception.Model;

public class UnprocessEntityException extends RuntimeException{
    public UnprocessEntityException(String message){
        super(message);
    }
}
