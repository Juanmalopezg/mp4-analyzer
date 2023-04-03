package com.example.analyzer.model;

import java.util.ArrayList;
import java.util.List;

public class Box {
    private final int size;
    private final BoxType type;
    private final List<Box> subBoxes;

    public Box(int size, String type) {
        this.size = size;
        this.type = BoxType.valueOf(type.toUpperCase());
        this.subBoxes = new ArrayList<>();
    }

    public int getSize() {
        return size;
    }

    public BoxType getType() {
        return type;
    }

    public List<Box> getSubBoxes() {
        return subBoxes;
    }

    public void addSubBox(Box subBox) {
        subBoxes.add(subBox);
    }
}