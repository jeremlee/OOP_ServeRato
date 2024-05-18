package com.example.gametest;

import com.example.utils.MySQLConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class MainApplication extends Application {
    public static float HEIGHT, WIDTH;
    static Stage myOnlyStage;
    @Override
    public void start(Stage stage) throws IOException {
        HEIGHT = 800;
        WIDTH = 1200;
        myOnlyStage = stage;
        myOnlyStage.setWidth(1200);
        myOnlyStage.setHeight(800);
        String createStatement = "CREATE TABLE IF NOT EXISTS tbluser ("
                + "uid INT AUTO_INCREMENT PRIMARY KEY,"
                + "username VARCHAR(255) NOT NULL,"
                + "password VARCHAR(255) NOT NULL"
                + ")";
        try(Connection conn = MySQLConnection.getConnection();
            Statement statement = conn.createStatement()){
            statement.executeUpdate(createStatement);
        }catch(SQLException e){
            e.printStackTrace();
        }
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("splash_screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Serverato");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}