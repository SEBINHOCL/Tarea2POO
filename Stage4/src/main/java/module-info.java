module com.example.stage1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.example.stage4 to javafx.fxml;
    exports com.example.stage4;
}
