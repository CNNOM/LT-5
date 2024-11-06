package com.example.task__5;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;

import java.io.File;

public class HelloController {

    @FXML
    private Pane indicatorPane;
    @FXML
    private Label startField, stopField, measureField;
    @FXML
    private Button startStopButton;
    @FXML
    private ImageView screen;
    @FXML
    private TextField delayField;
    @FXML
    private Button chooseFolderButton;

    private Aggregate aggregate;
    private Iterator iter;
    private Timeline time = new Timeline();
    private boolean isPlaying = false;
    private int currentImageIndex = 0;
    private Director director = new Director();

    public void initialize() {
        aggregate = new ConcreteAggregate("src/main/resources/img");
        iter = aggregate.getIterator();
        time.setCycleCount(Timeline.INDEFINITE);
        updateTimeline(1000);
        screen.setPreserveRatio(false);

        startField.setText("1");
        updateStopField();
        updateMeasureField();
    }

    // Обработчик события для показа кадров
    private class EvHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Image image = (Image) iter.next();
            if (image != null) {
                screen.setImage(image);
                currentImageIndex++;
                updateMeasureField();
            } else {
                iter.reset();
                currentImageIndex = 0;
                updateMeasureField();
                image = (Image) iter.next();
                if (image != null) {
                    screen.setImage(image);
                }
            }
        }
    }

    // Метод для переключения анимации
    @FXML
    public void toggleAnimation() {
        float start = Float.parseFloat(startField.getText());
        float stop = Float.parseFloat(stopField.getText());
        float measure = Float.parseFloat(measureField.getText());

        indicatorPane.getChildren().clear();

        Builder builder = new ConcreteBuilder();
        director.constructIndicator(builder, start, stop, measure);

        Indicator indicator = builder.build();
        indicator.show(indicatorPane);

        if (isPlaying) {
            time.pause();
            startStopButton.setText("⏹");
            indicatorPane.setVisible(false);
        } else {
            startStopButton.setText("▶");
            time.play();
            indicatorPane.setVisible(true);
        }
        isPlaying = !isPlaying;
    }

    // Метод для обновления временной шкалы с новой задержкой
    @FXML
    public void updateDelay() {
        if (delayField != null) {
            int newDelay = Integer.parseInt(delayField.getText());
            updateTimeline(newDelay);
        }
    }

    // Метод для обновления временной шкалы
    private void updateTimeline(int delayMillis) {
        time.stop();
        time.getKeyFrames().clear();
        time.getKeyFrames().add(new KeyFrame(Duration.millis(delayMillis), new EvHandler()));
        if (isPlaying) {
            time.play();
        }
    }

    // Метод для показа следующего изображения
    @FXML
    public void next() {
        Image image = (Image) iter.next();
        if (image != null) {
            screen.setImage(image);
            currentImageIndex++;
            updateMeasureField();
        } else {
            iter.reset();
            currentImageIndex = 0;
            updateMeasureField();
            image = (Image) iter.next();
            if (image != null) {
                screen.setImage(image);
            }
        }
    }

    // Метод для показа предыдущего изображения
    @FXML
    public void preview() {
        Image image = (Image) iter.preview();
        if (image != null) {
            screen.setImage(image);
            currentImageIndex--;
            updateMeasureField();
        }
    }

    // Метод для выбора папки с изображениями
    @FXML
    public void chooseFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(chooseFolderButton.getScene().getWindow());

        if (selectedDirectory != null) {
            aggregate = new ConcreteAggregate(selectedDirectory.getAbsolutePath());
            iter = aggregate.getIterator();
            currentImageIndex = 0;
            updateStopField();
            updateMeasureField();
            Image image = (Image) iter.next();
            if (image != null) {
                screen.setImage(image);
            }
        }
    }

    // Метод для обновления значения stopField
    private void updateStopField() {
        int imageCount = aggregate.getImageCount();
        stopField.setText(String.valueOf(imageCount));
    }

    // Метод для обновления значения measureField и перемещения флажка
    private void updateMeasureField() {
        measureField.setText(String.valueOf(currentImageIndex + 1));
        float measure = Float.parseFloat(measureField.getText());
        float start = Float.parseFloat(startField.getText());
        float stop = Float.parseFloat(stopField.getText());

        indicatorPane.getChildren().clear();

        Builder builder = new ConcreteBuilder();
        director.constructIndicator(builder, start, stop, measure);

        Indicator indicator = builder.build();
        indicator.show(indicatorPane);
    }
}