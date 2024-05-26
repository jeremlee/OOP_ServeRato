module com.example.gametest {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.media;


    opens com.example.gametest to javafx.fxml;
    opens com.example.gametest.utils to javafx.base;
    exports com.example.gametest;
}