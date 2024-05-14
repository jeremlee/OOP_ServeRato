package com.example.gametest;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreenController extends Controller{

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> switchScene("login_register.fxml"));
        pause.play();
    }
}
