package com.example.analyzer.exception;

import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AnalyzerExceptionHandler {

    @ExceptionHandler(InvalidFileFormatException.class)
    public ResponseEntity<String> handleInvalidFileFormatException(InvalidFileFormatException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(RuntimeJsonMappingException.class)
    public ResponseEntity<String> handleJsonMappingException(RuntimeJsonMappingException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
