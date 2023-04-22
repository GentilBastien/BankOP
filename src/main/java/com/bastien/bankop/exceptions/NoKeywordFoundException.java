package com.bastien.bankop.exceptions;

public class NoKeywordFoundException extends RuntimeException {
    public NoKeywordFoundException() {
        super();
    }

    public NoKeywordFoundException(String message) {
        super(message);
    }
}
