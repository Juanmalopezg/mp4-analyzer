package com.example.analyzer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AnalyzerApplicationTests {
    @Test
    public void main_shouldSuccessWithNoArgs() {
        AnalyzerApplication.main(new String[]{});
        assertNotNull(AnalyzerApplication.class);
    }
}
