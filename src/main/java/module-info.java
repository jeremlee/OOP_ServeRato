module com.example.gametest {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.gametest to javafx.fxml;
    exports com.example.gametest;
}