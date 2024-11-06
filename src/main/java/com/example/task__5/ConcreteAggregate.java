package com.example.task__5;

public class ConcreteAggregate implements Aggregate {
    private String basePath;

    public ConcreteAggregate(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public Iterator getIterator() {
        return new ConcreteIterator(basePath);
    }
}