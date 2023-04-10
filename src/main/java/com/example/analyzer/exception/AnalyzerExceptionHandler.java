package com.example.analyzer.exception;

import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AnalyzerExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body("Invalid File Format");
    }

    @ExceptionHandler(RuntimeJsonMappingException.class)
    public ResponseEntity<String> handleException(RuntimeJsonMappingException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
