package com.hotel.hotelapplication.exception;

public class RoleAlreadyExsistException extends RuntimeException {
    public RoleAlreadyExsistException(String message) {
        super(message);
    }
}
