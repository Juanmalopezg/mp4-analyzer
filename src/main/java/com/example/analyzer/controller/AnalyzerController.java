package com.example.analyzer.controller;

import com.example.analyzer.model.Box;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AnalyzerController {
    public static final String DEFAULT_URL = "https://demo.castlabs.com/tmp/text0.mp4";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/analyze")
    public Mono<ResponseEntity<String>> analyze(@RequestParam(value = "url", defaultValue = DEFAULT_URL) String url) {
        return Mono.fromCallable(() -> {
                    try (InputStream inputStream = new URI(url).toURL().openStream()) {
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                        return bufferedInputStream.readAllBytes();
                    }
                })
                .map(bytes -> {
                    ByteBuffer byteBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN);
                    List<Box> boxes = parseBoxes(byteBuffer, 0, bytes.length);
                    String json = null;
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

    private List<Box> parseBoxes(ByteBuffer byteBuffer, int offset, int length) {
        List<Box> boxes = new ArrayList<>();
        int end = offset + length;
        while (offset < end) {
            int boxSize = byteBuffer.getInt(offset);
            String boxType = new String(byteBuffer.array(), offset + 4, 4);
            Box box = new Box(boxSize, boxType);
            if (boxType.equals("moof") || boxType.equals("traf")) {
                List<Box> subBoxes = parseBoxes(byteBuffer, offset + 8, boxSize - 8);
                subBoxes.forEach(box::addSubBox);
            }
            boxes.add(box);
            offset += boxSize;
        }
        return boxes;
    }
}
