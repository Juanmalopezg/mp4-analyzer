package com.example.analyzer.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoxTest {
    @Test
    public void testGetSize() {
        Box box = new Box(10, "moov");
        int size = box.getSize();

        assertEquals(10, size);
    }

    @Test
    public void testGetType() {
        Box box = new Box(10, "moov");
        BoxType type = box.getType();

        assertEquals(BoxType.MOOV, type);
    }

    @Test
    public void testGetSubBoxes() {
        Box box = new Box(10, "moov");
        Box subBox1 = new Box(5, "mvhd");
        Box subBox2 = new Box(3, "mdat");
        box.addSubBox(subBox1);
        box.addSubBox(subBox2);

        List<Box> subBoxes = box.getSubBoxes();

        assertEquals(2, subBoxes.size());
        assertTrue(subBoxes.contains(subBox1));
        assertTrue(subBoxes.contains(subBox2));
    }

    @Test
    public void testAddSubBox() {
        Box box = new Box(10, "moov");
        Box subBox = new Box(5, "mvhd");

        box.addSubBox(subBox);

        List<Box> subBoxes = box.getSubBoxes();
        assertEquals(1, subBoxes.size());
        assertTrue(subBoxes.contains(subBox));
    }
}