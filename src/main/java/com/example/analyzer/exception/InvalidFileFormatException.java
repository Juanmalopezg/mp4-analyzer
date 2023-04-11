package com.example.analyzer.exception;

public class InvalidFileFormatException extends IllegalArgumentException {
    public InvalidFileFormatException(Throwable err) {
        super("Invalid File Format", err);
    }
}

