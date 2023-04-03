package com.example.analyzer.service;

import com.example.analyzer.model.Box;
import com.example.analyzer.model.BoxType;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoxService {
    public static final int BOX_HEADER_LENGTH = 4;

    public List<Box> processBox(ByteBuffer byteBuffer, int offset, int length) throws IllegalArgumentException {
        List<Box> boxes = new ArrayList<>();
        int end = offset + length;
        while (offset < end) {
            int boxSize = byteBuffer.getInt(offset);
            String boxType = new String(byteBuffer.array(), offset + BOX_HEADER_LENGTH, BOX_HEADER_LENGTH);
            Box box = new Box(boxSize, boxType);
            if (BoxType.NESTED_BOX_TYPES.get(boxType) != null) {
                List<Box> subBoxes = processBox(byteBuffer, offset + BOX_HEADER_LENGTH * 2, boxSize - BOX_HEADER_LENGTH * 2);
                subBoxes.forEach(box::addSubBox);
            }
            boxes.add(box);
            offset += boxSize;
        }
        return boxes;
    }
}
