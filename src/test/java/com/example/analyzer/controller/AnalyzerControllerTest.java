package com.example.analyzer.controller;

import com.example.analyzer.exception.InvalidFileFormatException;
import com.example.analyzer.service.BoxService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AnalyzerControllerTest {
    @Test
    void analyzeFile_shouldReturnCorrectJsonResponse() {
        AnalyzerController analyzerController = new AnalyzerController(new BoxService());
        String url = "https://demo.castlabs.com/tmp/text0.mp4";
        ResponseEntity<String> expectedResponse = ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body("[{\"size\":181,\"type\":\"MOOF\",\"subBoxes\":[{\"size\":16,\"type\":\"MFHD\",\"subBoxes\":[]},{\"size\":157,\"type\":\"TRAF\",\"subBoxes\":[{\"size\":24,\"type\":\"TFHD\",\"subBoxes\":[]},{\"size\":20,\"type\":\"TRUN\",\"subBoxes\":[]},{\"size\":44,\"type\":\"UUID\",\"subBoxes\":[]},{\"size\":61,\"type\":\"UUID\",\"subBoxes\":[]}]}]},{\"size\":17908,\"type\":\"MDAT\",\"subBoxes\":[]}]");

        Mono<ResponseEntity<String>> actualResponse = analyzerController.analyzeFile(url);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.block().getStatusCode());
        assertEquals(expectedResponse.getHeaders(), actualResponse.block().getHeaders());
        assertEquals(expectedResponse.getBody(), actualResponse.block().getBody());
    }

    @Test
    void analyzeFile_shouldReturnBadRequestResponse_whenUrlIsIncorrect() {
        AnalyzerController analyzerController = new AnalyzerController(new BoxService());
        String url = "htp:/wrong-url";
        ResponseEntity<String> expectedResponse = ResponseEntity.badRequest().body("Invalid URL");

        Mono<ResponseEntity<String>> actualResponse = analyzerController.analyzeFile(url);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.block().getStatusCode());
        assertEquals(expectedResponse.getHeaders(), actualResponse.block().getHeaders());
        assertEquals(expectedResponse.getBody(), actualResponse.block().getBody());
    }

    @Test
    void analyzeFile_shouldThrowInvalidFileFormatExceptionException_onInvalidFileFormat() {
        AnalyzerController analyzerController = new AnalyzerController(new BoxService());
        String url = "http://demo.castlabs.com/tmp/text0.mp4";

        Mono<ResponseEntity<String>> actualResponse = analyzerController.analyzeFile(url);

        assertThrows(InvalidFileFormatException.class, actualResponse::block);
    }

    @Test
    void analyzeFile_shouldThrowRuntimeJsonMappingException_whenObjectMapperFails() throws JsonProcessingException {
        BoxService boxService = Mockito.mock(BoxService.class);
        AnalyzerController analyzerController = new AnalyzerController(boxService);
        ObjectMapper objectMapper = Mockito.mock(ObjectMapper.class);
        analyzerController.objectMapper = objectMapper;
        String url = "https://demo.castlabs.com/tmp/text0.mp4";
        Mockito.when(objectMapper.writeValueAsString(Mockito.any())).thenThrow(new JsonProcessingException("") {
        });

        Mono<ResponseEntity<String>> actualResponse = analyzerController.analyzeFile(url);

        assertThrows(RuntimeJsonMappingException.class, actualResponse::block);
    }
}
