package com.example.gametest;

import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class LevelsController extends Controller {
    public static Integer LEVEL;
    public Button btnLvl1;
    public Button btnLvl2;
    public Button btnLvl3;
    public Button btnLvl4;
    public Button btnLvl5;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {LEVEL = 0;}
    public void onLvl1Click(){
        LEVEL = 1;
        switchScene("game.fxml");
    }
    public void onLvl2Click(){
        LEVEL = 2;
        switchScene("game.fxml");
    }
    public void onLvl3Click(){
        LEVEL = 3;
        switchScene("game.fxml");
    }
    public void onLvl4Click(){
         LEVEL = 4;
        switchScene("game.fxml");
    }
    public void onLvl5Click(){
        LEVEL = 5;
        switchScene("game.fxml");
    }
}
