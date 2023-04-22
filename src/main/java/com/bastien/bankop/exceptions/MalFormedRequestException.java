package com.bastien.bankop.exceptions;

public class MalFormedRequestException extends RuntimeException {

    public MalFormedRequestException() {
        super();
    }

    public MalFormedRequestException(String message) {
        super("Request does not contain a " + message);
    }
}
