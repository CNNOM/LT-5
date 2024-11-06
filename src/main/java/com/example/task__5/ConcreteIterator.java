package com.example.task__5;

import javafx.scene.image.Image;

import java.util.List;

public class ConcreteIterator implements Iterator {
    private List<Image> images;
    private int currentIndex = 0;

    public ConcreteIterator(List<Image> images) {
        this.images = images;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < images.size();
    }

    @Override
    public Object next() {
        if (hasNext()) {
            Image image = images.get(currentIndex);
            currentIndex++;
            return image;
        }
        return null;
    }

    @Override
    public Object preview() {
        if (currentIndex > 0) {
            currentIndex--;
            return images.get(currentIndex);
        }
        return null;
    }

    @Override
    public void reset() {
        currentIndex = 0;
    }
}