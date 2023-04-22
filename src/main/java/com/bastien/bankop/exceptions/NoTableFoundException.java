package com.bastien.bankop.exceptions;

public class NoTableFoundException extends RuntimeException {
    public NoTableFoundException() {
        super();
    }

    public NoTableFoundException(String message) {
        super(message);
    }
}
