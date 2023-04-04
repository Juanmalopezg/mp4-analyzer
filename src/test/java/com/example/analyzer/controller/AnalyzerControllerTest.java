package com.example.analyzer.controller;

import com.example.analyzer.service.BoxService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
    public void analyzeFile_shouldReturnBadRequestResponse_whenFileHasInvalidFormat() {
        AnalyzerController analyzerController = new AnalyzerController(new BoxService());
        String url = "http://demo.castlabs.com/tmp/text0.mp4";
        ResponseEntity<String> expectedResponse = ResponseEntity.badRequest().body("Invalid File Format");

        Mono<ResponseEntity<String>> actualResponse = analyzerController.analyzeFile(url);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.block().getStatusCode());
        assertEquals(expectedResponse.getHeaders(), actualResponse.block().getHeaders());
        assertEquals(expectedResponse.getBody(), actualResponse.block().getBody());
    }

    @Test
    public void analyzeFile_shouldReturnBadRequestResponse_whenJsonProcessingExceptionIsThrown() throws JsonProcessingException {
        BoxService boxServiceMock = Mockito.mock(BoxService.class);
        AnalyzerController analyzerController = new AnalyzerController(boxServiceMock);
        String url = "https://demo.castlabs.com/tmp/text0.mp4";
        ObjectMapper objectMapperMock = Mockito.mock(ObjectMapper.class);
        Mockito.when(objectMapperMock.writeValueAsString(Mockito.any())).thenThrow(new JsonProcessingException("Error") {
        });
        analyzerController.objectMapper = objectMapperMock;

        Mono<ResponseEntity<String>> actualResponse = analyzerController.analyzeFile(url);

        ResponseEntity<String> expectedResponse = ResponseEntity.badRequest().body("Error");
        assertEquals(expectedResponse.getStatusCode(), actualResponse.block().getStatusCode());
        assertEquals(expectedResponse.getHeaders(), actualResponse.block().getHeaders());
        assertEquals(expectedResponse.getBody(), actualResponse.block().getBody());
    }

    @Test
    public void analyzeFile_shouldReturnBadRequestResponse_whenExceptionIsThrown() {
        BoxService boxServiceMock = Mockito.mock(BoxService.class);
        AnalyzerController analyzerController = new AnalyzerController(boxServiceMock);
        String url = "https://demo.castlabs.com/tmp/text0.mp4";
        Mockito.when(boxServiceMock.processBox(Mockito.any(), Mockito.anyInt(), Mockito.anyInt())).thenThrow(new RuntimeException("Error"));

        Mono<ResponseEntity<String>> actualResponse = analyzerController.analyzeFile(url);

        ResponseEntity<String> expectedResponse = ResponseEntity.badRequest().body("Error");
        assertEquals(expectedResponse.getStatusCode(), actualResponse.block().getStatusCode());
        assertEquals(expectedResponse.getHeaders(), actualResponse.block().getHeaders());
        assertEquals(expectedResponse.getBody(), actualResponse.block().getBody());
    }
}
