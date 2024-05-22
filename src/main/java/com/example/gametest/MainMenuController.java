package com.example.gametest;

import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController extends Controller{
    public Button btnPlay;
    public Button btnLeaderboards;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //wala ra so far
    }
    public void onPlayClick(){
        switchScene("levels.fxml");
    }
    public void onLeaderboardsClick(){

    }

}
