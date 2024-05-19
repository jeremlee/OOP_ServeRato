package com.example.gametest;

import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController extends Controller{
    public Button btnLogin;
    public Button btnRegister;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //wala ra so far
    }

    public void onLoginClick(){
        switchScene("login.fxml");
    }
    public void onRegisterClick(){
        switchScene("register.fxml");
    }

}
