package com.example.analyzer.controller;

import com.example.analyzer.model.Box;
import com.example.analyzer.service.BoxService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public static final String DEFAULT_URL = "https://demo.castlabs.com/tmp/text0.mp4";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BoxService boxService;

    public AnalyzerController(BoxService boxService) {
        this.boxService = boxService;
    }

    @GetMapping("/analyze")
    public Mono<ResponseEntity<String>> analyzeFile(@RequestParam(value = "url", defaultValue = DEFAULT_URL) String url) {
        if (!isValidUrl(url)) {
            return Mono.just(ResponseEntity.badRequest().body("Invalid URL"));
        }

        HttpClient client = HttpClient.create();
        return client.get()
                .uri(url)
                .responseContent()
                .aggregate()
                .asByteArray()
                .flatMap(s -> {
                    ByteBuffer byteBuffer = ByteBuffer.wrap(s);

                    try {
                        List<Box> boxes = boxService.processBox(byteBuffer, 0, s.length);
                        return Mono.just(boxes);
                    } catch (IllegalArgumentException e) {
                        return Mono.error(e);
                    }
                })
                .map(boxes -> {
                    try {
                        String json = objectMapper.writeValueAsString(boxes);
                        return ResponseEntity.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(json);
                    } catch (JsonProcessingException e) {
                        return ResponseEntity.badRequest().body(e.getMessage());
                    }
                })
                .onErrorResume(e -> {
                    if (e instanceof IllegalArgumentException) {
                        return Mono.just(ResponseEntity.badRequest().body("Invalid File Format"));
                    } else {
                        return Mono.just(ResponseEntity.badRequest().body(e.getMessage()));
                    }
                });
    }

    private boolean isValidUrl(String url) {
        String regex = "^https?://.+\\.mp4$";
        return url.matches(regex);
    }

}
