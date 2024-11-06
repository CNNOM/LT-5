package com.example.task__5;

public interface Iterator {
    boolean hasNext();
    Object next();
    Object preview();
    void reset();
}