package com.example.analyzer.controller;

import com.example.analyzer.model.Box;
import com.example.analyzer.service.BoxService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.nio.ByteBuffer;
import java.util.List;

@RestController
public class AnalyzerController {
    private static final String DEFAULT_URL = "https://demo.castlabs.com/tmp/text0.mp4";
    private static final String URL_REGEX = "^https?://.+\\.mp4$";
    ObjectMapper objectMapper = new ObjectMapper();
    private final BoxService boxService;
    private final HttpClient httpClient;

    public AnalyzerController(BoxService boxService) {
        this.boxService = boxService;
        this.httpClient = HttpClient.create();
    }

    @GetMapping("/analyze")
    public Mono<ResponseEntity<String>> analyzeFile(@RequestParam(value = "url", defaultValue = DEFAULT_URL) String url) {
        if (!isValidUrl(url)) {
            return Mono.just(ResponseEntity.badRequest().body("Invalid URL"));
        }

        return fetchVideoData(url)
                .flatMap(this::getBoxes)
                .map(this::createJsonResponse);
    }

    ResponseEntity<String> createJsonResponse(List<Box> boxes) {
        try {
            String json = objectMapper.writeValueAsString(boxes);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeJsonMappingException(e.getMessage());
        }
    }

    private boolean isValidUrl(String url) {
        return url.matches(URL_REGEX);
    }

    Mono<byte[]> fetchVideoData(String url) {
        return httpClient.get()
                .uri(url)
                .responseContent()
                .aggregate()
                .asByteArray();
    }

    Mono<List<Box>> getBoxes(byte[] data) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(data);

        List<Box> boxes = boxService.processBox(byteBuffer, 0, data.length);
        return Mono.just(boxes);
    }
}
