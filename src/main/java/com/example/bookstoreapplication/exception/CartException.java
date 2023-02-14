package com.example.bookstoreapplication.exception;

public class CartException extends RuntimeException{
    public CartException(String message){
        super(message);
    }
}