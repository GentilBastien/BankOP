package com.bastien.bankop.exceptions;

public class TableNameException extends RuntimeException {
    public TableNameException() {
        super();
    }
    public TableNameException(String message) {
        super(message);
    }
}
