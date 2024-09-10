package com.employees.exceptions;

public class NoSuchSupplierExistException extends RuntimeException {
    public NoSuchSupplierExistException() {}

    public NoSuchSupplierExistException(String message) {
        super(message);
    }
}
