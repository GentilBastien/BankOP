package com.bastien.bankop.exceptions;

public class MalFormedDTOException extends RuntimeException {
    public MalFormedDTOException(String message) {
        super(message);
    }
}
