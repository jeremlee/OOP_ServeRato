package com.example.gametest;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController extends Controller{
    public ImageView imgPlay;
    public ImageView imgLeaderboards;
    public ImageView imgExit;
    public ImageView imgLogout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //wala ra so far
    }
    public void onPlayClick(){
        toLoadingScreen("levels.fxml");
    }
    public void onLeaderboardsClick(){

    }
    public void onExitClick(){
        Platform.exit();
    }
    public void onLogoutClick() { toLoadingScreen("login.fxml"); }

    @FXML
    void onPlayerHoverIn(){
        hoverIn(imgPlay);
    }

    @FXML
    void onPlayerHoverOut(){
        hoverOut(imgPlay);
    }

    @FXML
    void onLeaderboardsHoverIn(){
        hoverIn(imgLeaderboards);
    }
    @FXML
    void onLeaderboardsHoverOut(){
        hoverOut(imgLeaderboards);
    }
    @FXML
    void onLogoutHoverIn(){
        hoverIn(imgLogout);
    }
    @FXML
    void onLogoutHoverOut(){
        hoverOut(imgLogout);
    }
    @FXML
    void onExitHoverIn(){
        hoverIn(imgExit);
    }
    @FXML
    void onExitHoverOut(){
        hoverOut(imgExit);
    }

}
