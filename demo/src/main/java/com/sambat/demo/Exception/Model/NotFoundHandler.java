package com.sambat.demo.Exception.Model;

public class NotFoundHandler extends RuntimeException{
    public NotFoundHandler(String message){
        super(message);
    }
}
