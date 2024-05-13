package com.example.gametest;

import com.example.utils.User;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class Controller implements Initializable {
    Stage stage;
    protected User USER = User.getInstance();
    public abstract void switchScene(String fxmlFile);
}
