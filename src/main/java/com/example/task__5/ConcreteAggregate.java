package com.example.task__5;

import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConcreteAggregate implements Aggregate {
    private List<Image> images;

    public ConcreteAggregate(String basePath) {
        this.images = new ArrayList<>();
        loadImages(basePath);
    }

    private void loadImages(String directoryPath) {
        File directory = new File(directoryPath);
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && isImageFile(file)) {
                        images.add(new Image(file.toURI().toString()));
                    }
                }
            }
        }
    }

    private boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".gif");
    }

    @Override
    public Iterator getIterator() {
        return new ConcreteIterator(images);
    }

    @Override
    public int getImageCount() {
        return images.size();
    }
}