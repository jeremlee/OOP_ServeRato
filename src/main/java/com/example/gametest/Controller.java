package com.example.gametest;

import com.example.utils.User;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.gametest.MainApplication.myOnlyStage;

public abstract class Controller implements Initializable {

    protected User USER = User.getInstance();
    public void switchScene(String fxmlFile){
        try{
            Parent p = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene s = new Scene(p);
            myOnlyStage.setScene(s);
            myOnlyStage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
