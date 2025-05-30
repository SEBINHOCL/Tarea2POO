module com.example.stage1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.stage1 to javafx.fxml;
    exports com.example.stage1;
}