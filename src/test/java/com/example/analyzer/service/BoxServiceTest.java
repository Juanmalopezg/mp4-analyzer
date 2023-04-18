package com.example.analyzer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.example.analyzer.model.Box;
import com.example.analyzer.model.BoxType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoxServiceTest {

    private BoxService boxService;

    @BeforeEach
    void setUp() {
        boxService = new BoxService();
    }

    @Test
    void testProcessSimpleBox() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(24);
        byteBuffer.putInt(24);
        byteBuffer.put("trun".getBytes(StandardCharsets.UTF_8));
        byteBuffer.flip();

        List<Box> boxes = boxService.processBoxes(byteBuffer, byteBuffer.limit());
        assertEquals(1, boxes.size());

        Box box = boxes.get(0);
        assertEquals(24, box.getSize());
        assertEquals(BoxType.TRUN, box.getType());
        assertEquals(0, box.getSubBoxes().size());
    }

    @Test
    void testProcessNestedBox() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(46);
        byteBuffer.putInt(24);
        byteBuffer.put("moof".getBytes(StandardCharsets.UTF_8));
        byteBuffer.putInt(16);
        byteBuffer.put("mfhd".getBytes(StandardCharsets.UTF_8));
        byteBuffer.flip();

        List<Box> boxes = boxService.processBoxes(byteBuffer, byteBuffer.limit());
        assertEquals(1, boxes.size());

        Box box1 = boxes.get(0);
        assertEquals(24, box1.getSize());
        assertEquals(BoxType.MOOF, box1.getType());
        assertEquals(1, box1.getSubBoxes().size());

        Box box2 = box1.getSubBoxes().get(0);
        assertEquals(16, box2.getSize());
        assertEquals(BoxType.MFHD, box2.getType());
    }
}
