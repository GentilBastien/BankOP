package com.bastien.bankop.exceptions;

public class NoOperationFoundException extends RuntimeException {
    public NoOperationFoundException() {
        super();
    }

    public NoOperationFoundException(String message) {
        super(message);
    }
}
