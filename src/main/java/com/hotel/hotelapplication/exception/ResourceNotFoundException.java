package com.hotel.hotelapplication.exception;

public class ResourceNotFoundException extends RuntimeException {
    public  ResourceNotFoundException(String message) {
        super(message);
    }
}
