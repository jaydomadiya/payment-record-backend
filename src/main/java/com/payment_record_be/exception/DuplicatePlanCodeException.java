package com.payment_record_be.exception;

public class DuplicatePlanCodeException extends RuntimeException {

    public DuplicatePlanCodeException(String message) {
        super(message);
    }
}
