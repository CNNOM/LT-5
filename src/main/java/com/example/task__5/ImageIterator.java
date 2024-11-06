package com.example.task__5;

import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageIterator implements Iterator {
    private int current = 0;
    private String basePath;
    private List<File> imageFiles;

    public ImageIterator(String basePath) {
        this.basePath = basePath;
        this.imageFiles = new ArrayList<>();
        loadImageFiles();
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
        current = 0;
        loadImageFiles();
    }

    private void loadImageFiles() {
        imageFiles.clear();
        File folder = new File(basePath);
        if (folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg"));
            if (files != null) {
                for (File file : files) {
                    imageFiles.add(file);
                }
            }
        }
    }

    private Image getImage(int index) {
        if (index >= 0 && index < imageFiles.size()) {
            return new Image(imageFiles.get(index).toURI().toString());
        }
        return null;
    }

    @Override
    public boolean hasNext() {
        return current + 1 < imageFiles.size();
    }

    @Override
    public Object next() {
        if (hasNext()) {
            current++;
            return getImage(current);
        }
        return null;
    }

    @Override
    public Object preview() {
        if (current > 0) {
            current--;
            return getImage(current);
        }
        return null;
    }
}