package com.example.analyzer.controller;

import com.example.analyzer.model.Box;
import com.example.analyzer.service.BoxService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public static final String DEFAULT_URL = "https://demo.castlabs.com/tmp/text0.mp4";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BoxService boxService;

    public AnalyzerController(BoxService boxService) {
        this.boxService = boxService;
    }

    @GetMapping("/analyze")
    public Mono<ResponseEntity<String>> analyze(@RequestParam(value = "url", defaultValue = DEFAULT_URL) String url) {
        HttpClient client = HttpClient.create();
        return client.get()
                .uri(url)
                .responseContent()
                .aggregate()
                .asByteArray()
                .map(s -> {
                    ByteBuffer byteBuffer = ByteBuffer.wrap(s);
                    List<Box> boxes = boxService.analyze(byteBuffer, 0, s.length);
                    String json;
                    try {
                        json = objectMapper.writeValueAsString(boxes);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    return json;
                })
                .map(json -> ResponseEntity.ok()
                        .header("Content-Type", "application/json")
                        .body(json))
                .onErrorResume(error -> Mono.just(ResponseEntity.badRequest().body(error.getMessage())));
    }
}
