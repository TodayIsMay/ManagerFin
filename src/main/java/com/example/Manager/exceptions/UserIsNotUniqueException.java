package com.example.Manager.exceptions;

public class UserIsNotUniqueException extends RuntimeException {
    public UserIsNotUniqueException(String message) {
        super(message);
    }
}