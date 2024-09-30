package com.example.task__5;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


public class HelloController {
    @FXML
    private Pane indicatorPane;
    @FXML
    private TextField   startField, stopField, measureField, titleField;
    
    @FXML
    public void createIndicators(TextField startField, TextField stopField, TextField measureField, TextField  titleField) {
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void createIndicator() {
        this.createIndicators(startField, stopField, measureField, titleField);
    }
}