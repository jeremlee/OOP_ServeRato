package com.example.gametest;

import com.example.utils.User;
import javafx.stage.Stage;

public abstract class Controller {
    Stage stage;
    protected User USER = User.getInstance();
    protected void setStage(Stage stage){
        this.stage = stage;
    }
}
