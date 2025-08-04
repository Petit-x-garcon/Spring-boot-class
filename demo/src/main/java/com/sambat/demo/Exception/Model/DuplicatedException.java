package com.sambat.demo.Exception.Model;

public class DuplicatedException extends RuntimeException{
    public DuplicatedException(String message){
        super(message);
    }
}
