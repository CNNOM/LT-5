package com.example.task__5;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;

import java.io.File;

public class HelloController {

    // Поля для управления индикаторами
    @FXML
    private Pane indicatorPane;
    @FXML
    private TextField startField, stopField, measureField, titleField;

    // Поля для управления анимацией изображений
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

    public void initialize() {
        // Инициализация для анимации изображений
        aggregate = new ConcreteAggregate("src/main/resources/img");
        iter = aggregate.getIterator();
        time.setCycleCount(Timeline.INDEFINITE);
        updateTimeline(1000);
        screen.setPreserveRatio(false);
    }

    // Метод для создания индикаторов
    @FXML
    public void createIndicator() {
        float start = Float.parseFloat(startField.getText());
        float stop = Float.parseFloat(stopField.getText());
        float measure = Float.parseFloat(measureField.getText());
        String title = titleField.getText();

        if (measure < start || measure > stop) {
            showAlert("Ошибка", "Введенное значение не входит в диапазон между " + start + " и " + stop);
            return;
        }

        indicatorPane.getChildren().clear();

        Builder builder = new ConcreteBuilder();

        builder.lineBounds(start, stop);
        builder.linePaint(measure);
        builder.lineMark(String.format("%.1f", measure));
        builder.addTitle(title);

        Indicator indicator = builder.build();
        indicator.show(indicatorPane);
    }

    // Метод для показа ошибок
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Обработчик события для показа кадров
    private class EvHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Image image = (Image) iter.next();
            if (image != null) {
                screen.setImage(image);
            }
        }
    }

    // Метод для переключения анимации
    @FXML
    public void toggleAnimation() {
        if (isPlaying) {
            time.pause();
            startStopButton.setText("⏹");
        } else {
            startStopButton.setText("▶");
            time.play();
        }
        isPlaying = !isPlaying;
    }

    // Метод для обновления временной шкалы с новой задержкой
    @FXML
    public void updateDelay() {
        int newDelay = Integer.parseInt(delayField.getText());
        updateTimeline(newDelay);
    }

    // Метод для обновления временной шкалы
    private void updateTimeline(int delayMillis) {
        time.stop();
        time.getKeyFrames().clear();
        time.getKeyFrames().add(new KeyFrame(Duration.millis(delayMillis), new EvHandler()));
        if (isPlaying) {
            time.play(); // возобновляем
        }
    }

    // Метод для показа следующего изображения
    @FXML
    public void next() {
        Image image = (Image) iter.next();
        if (image != null) {
            screen.setImage(image);
        }
    }

    // Метод для показа предыдущего изображения
    @FXML
    public void preview() {
        Image image = (Image) iter.preview();
        if (image != null) {
            screen.setImage(image);
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
            Image image = (Image) iter.next();
            if (image != null) {
                screen.setImage(image);
            }
        }
    }
}