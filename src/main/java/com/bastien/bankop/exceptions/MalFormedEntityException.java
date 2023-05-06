package com.bastien.bankop.exceptions;

public class MalFormedEntityException extends RuntimeException {
    public MalFormedEntityException(String message) {
        super(message);
    }
}
