package com.example.analyzer.exception;

import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class AnalyzerExceptionHandlerTest {
    @Test
    public void handleIllegalArgumentExceptionTest() {
        AnalyzerExceptionHandler handler = new AnalyzerExceptionHandler();
        IllegalArgumentException ex = new IllegalArgumentException("Test Exception");

        ResponseEntity<String> response = handler.handleIllegalArgumentException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid File Format", response.getBody());
    }

    @Test
    public void handleRuntimeJsonMappingExceptionTest() {
        AnalyzerExceptionHandler handler = new AnalyzerExceptionHandler();
        RuntimeJsonMappingException ex = new RuntimeJsonMappingException("Test Exception");

        ResponseEntity<String> response = handler.handleException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Test Exception", response.getBody());
    }
}