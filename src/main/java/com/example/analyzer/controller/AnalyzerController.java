package com.example.analyzer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalyzerController {
    public static final String defaultUrl = "http://demo.castlabs.com/tmp/text0.mp4";

    @GetMapping("/analyze")
    public String analyze(@RequestParam(value = "url", defaultValue = defaultUrl) String url) {
        return String.format("Analyzing %s!", url);
    }
}
