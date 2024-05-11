package com.example.gametest;

import com.example.utils.MySQLConnection;
import com.example.utils.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class MainApplication extends Application {
    public static float HEIGHT, WIDTH;
    @Override
    public void start(Stage stage) throws IOException {
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
        HEIGHT = 1200f;
        WIDTH = 800f;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login_register.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HEIGHT, WIDTH);
        stage.setTitle("Serverato");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}