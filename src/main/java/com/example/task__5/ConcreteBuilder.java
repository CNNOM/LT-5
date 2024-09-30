package com.example.task__5;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;


public class ConcreteBuilder implements  Builder{

    Indicator indicator = new Indicator();

    private float start, stop;


    @Override
    public void setView(int N, char norm, char select) {

        indicator.setLenght(N);
        indicator.setPaint(norm);
        indicator.setMetka(select);

    }


    @Override
    public void lineBounds(float start, float stop) {

        this.start = start;
        this.stop = stop;
        FlowPane pane = new FlowPane();
        Text text = new Text("" + start);
        Line line = new Line(5, 5, 200, 5);
        Text text1 = new Text("" + stop);

        pane.getChildren().addAll(text, line, text1);

        indicator.add(pane);

    }


    @Override
    public void linePaint(float measure) {
        AnchorPane pane = new AnchorPane();
        double fixedWidth = 200; // Фиксированная ширина

        double offset = fixedWidth * start / (stop - start);

        double x = fixedWidth * measure / (stop - start) - offset;
        System.out.println(stop + " " + start + " " + measure + " " + x + " " + pane.getWidth());

        SVGPath svgPath = new SVGPath();

        svgPath.setContent("M 7,1 C 4.929086,1 3.25,2.67871 3.25,4.75 3.25,5.33008 3.381836,5.88013 3.617312,6.37012 3.630484,6.39796 7,13 7,13 L 10.34205,6.45215 C 10.60279,5.94166 10.75,5.36303 10.75,4.75 10.75,2.67871 9.07129,1 7,1 Z M 7,7 C 5.757437,7 4.75,5.99219 4.75,4.75 4.75,3.50781 5.757437,2.5 7,2.5 8.24219,2.5 9.25,3.50781 9.25,4.75 9.25,5.99219 8.24219,7 7,7 Z");
        svgPath.setFill(Color.RED);
        svgPath.setStroke(Color.RED);
        svgPath.setScaleX(2.0);
        svgPath.setScaleY(2.0);
        svgPath.setTranslateX(x - 7);
        svgPath.setTranslateY(-13);

        pane.getChildren().add(svgPath);

        indicator.add(pane);
    }
    @Override
    public void lineMark(String measure) {
        AnchorPane pane = new AnchorPane();
        Text markText = new Text(measure);
        markText.setX(50);
        markText.setY(50);

        pane.getChildren().add(markText);

        indicator.add(pane);
    }

    @Override
    public void addTitle(String name) {
        AnchorPane pane = new AnchorPane();
        Text titleText = new Text(name);
        titleText.setX(50);
        titleText.setY(70);

        pane.getChildren().add(titleText);

        indicator.add(pane);
    }

    public Indicator build() {
        return indicator;
    }
}
