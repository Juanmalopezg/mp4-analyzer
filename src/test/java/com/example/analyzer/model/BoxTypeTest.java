package com.example.analyzer.model;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BoxTypeTest {
    @Test
    public void testNestedBoxTypes() {
        Map<String, BoxType> nestedBoxTypes = BoxType.NESTED_BOX_TYPES;

        assertTrue(nestedBoxTypes.containsKey("moof"));
        assertTrue(nestedBoxTypes.containsKey("moov"));
        assertTrue(nestedBoxTypes.containsKey("traf"));
        assertTrue(nestedBoxTypes.containsKey("trak"));

        assertEquals(BoxType.MOOF, nestedBoxTypes.get("moof"));
        assertEquals(BoxType.MOOV, nestedBoxTypes.get("moov"));
        assertEquals(BoxType.TRAF, nestedBoxTypes.get("traf"));
        assertEquals(BoxType.TRAK, nestedBoxTypes.get("trak"));
    }

    @Test
    public void testBoxTypeValues() {
        assertEquals(BoxType.MOOV, BoxType.valueOf("MOOV"));
        assertEquals(BoxType.MOOF, BoxType.valueOf("MOOF"));
        assertEquals(BoxType.MVHD, BoxType.valueOf("MVHD"));
        assertEquals(BoxType.MFHD, BoxType.valueOf("MFHD"));
        assertEquals(BoxType.TRAK, BoxType.valueOf("TRAK"));
        assertEquals(BoxType.TRAF, BoxType.valueOf("TRAF"));
        assertEquals(BoxType.TFHD, BoxType.valueOf("TFHD"));
        assertEquals(BoxType.TRUN, BoxType.valueOf("TRUN"));
        assertEquals(BoxType.UUID, BoxType.valueOf("UUID"));
        assertEquals(BoxType.MDAT, BoxType.valueOf("MDAT"));
    }
}