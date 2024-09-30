package com.example.task__5;

import javafx.scene.layout.Pane;

public class Indicator {
    private Pane panel = new Pane();

    public void add(Pane pane) {
        panel.getChildren().add(pane);
    }

    public void show(Pane pane) {
        pane.getChildren().add(panel);
    }

    public void setLenght(int n) {}

    public void setPaint(char norm) {}

    public void setMetka(char select) {}
}
