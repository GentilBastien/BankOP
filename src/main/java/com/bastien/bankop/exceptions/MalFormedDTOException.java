package com.bastien.bankop.exceptions;

public class MalFormedDTOException extends RuntimeException {

    public MalFormedDTOException() {
        super();
    }

    public MalFormedDTOException(String message) {
        super("DTO does not contain a " + message);
    }
}
