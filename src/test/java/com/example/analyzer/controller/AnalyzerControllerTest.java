package com.example.analyzer.controller;

import com.example.analyzer.service.BoxService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnalyzerControllerTest {
    @Test
    public void analyzeFile_shouldReturnCorrectJsonResponse() {
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
    public void analyzeFile_shouldReturnBadRequestResponse_whenUrlIsIncorrect() {
        AnalyzerController analyzerController = new AnalyzerController(new BoxService());
        String url = "htp:/wrong-url";
        ResponseEntity<String> expectedResponse = ResponseEntity.badRequest().body("Invalid URL");

        Mono<ResponseEntity<String>> actualResponse = analyzerController.analyzeFile(url);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.block().getStatusCode());
        assertEquals(expectedResponse.getHeaders(), actualResponse.block().getHeaders());
        assertEquals(expectedResponse.getBody(), actualResponse.block().getBody());
    }

    @Test
    public void analyzeFile_shouldReturnBadRequestResponse_whenFileIsNotMPEG4Part12Format() {
        AnalyzerController analyzerController = new AnalyzerController(new BoxService());
        String url = "http://demo.castlabs.com/tmp/text0.mp4"; // A non-MPEG4 Part 12 file
        ResponseEntity<String> expectedResponse = ResponseEntity.badRequest().body("Invalid File Format");

        Mono<ResponseEntity<String>> actualResponse = analyzerController.analyzeFile(url);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.block().getStatusCode());
        assertEquals(expectedResponse.getHeaders(), actualResponse.block().getHeaders());
        assertEquals(expectedResponse.getBody(), actualResponse.block().getBody());
    }
}
