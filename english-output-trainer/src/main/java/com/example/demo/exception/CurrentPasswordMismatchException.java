package com.example.demo.exception;

public class CurrentPasswordMismatchException extends RuntimeException {

    public CurrentPasswordMismatchException(String message) {
        super(message);
    }

}