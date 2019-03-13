package com.pavlik.exception;

public class EmptySearchStringException extends RuntimeException {

    public EmptySearchStringException() {
        super("String to search must be set");
    }
}
