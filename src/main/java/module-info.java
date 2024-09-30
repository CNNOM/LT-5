module com.example.task__5 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.task__5 to javafx.fxml;
    exports com.example.task__5;
}