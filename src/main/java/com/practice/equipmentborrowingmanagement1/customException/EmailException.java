package com.practice.equipmentborrowingmanagement1.customException;

public class EmailException extends RuntimeException {
    public EmailException(String message) {
        super(message);
    }
}
