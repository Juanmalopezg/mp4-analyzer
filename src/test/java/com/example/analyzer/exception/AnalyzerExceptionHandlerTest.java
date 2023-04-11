package com.example.analyzer.exception;

import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class AnalyzerExceptionHandlerTest {

    AnalyzerExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new AnalyzerExceptionHandler();
    }

    @Test
    public void handleInvalidFileFormatExceptionTest() {
        Exception ex = new Exception();
        InvalidFileFormatException invalidFileFormatException = new InvalidFileFormatException(ex);

        ResponseEntity<String> response = handler.handleInvalidFileFormatException(invalidFileFormatException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid File Format", response.getBody());
    }

    @Test
    public void handleJsonMappingExceptionTest() {
        RuntimeJsonMappingException ex = new RuntimeJsonMappingException("Test Exception");

        ResponseEntity<String> response = handler.handleJsonMappingException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Test Exception", response.getBody());
    }
}