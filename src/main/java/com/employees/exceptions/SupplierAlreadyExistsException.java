package com.employees.exceptions;

public class SupplierAlreadyExistsException extends RuntimeException {
    public SupplierAlreadyExistsException() {}

    public SupplierAlreadyExistsException(String message) {
        super(message);
    }
}
