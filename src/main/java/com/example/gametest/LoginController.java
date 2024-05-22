package com.example.gametest;

import com.example.utils.MySQLConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController extends Controller {
    public Button btnLogin;
    public TextField txtfUsername;
    public PasswordField txtfPassword;
    public Label lblErrorMsg;
    public Label lblToRegister;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}//wala ra
    @FXML
    public void onLoginClick(){
        lblErrorMsg.setText("");
        readData(txtfUsername.getText(),txtfPassword.getText());
    }
    public void onLoginEnter(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER){
            readData(txtfUsername.getText(),txtfPassword.getText());
        }
    }
    public void readData(String name, String password){
        if(name.isBlank() || password.isBlank()){
            lblErrorMsg.setText("Please fill up the fields.");
            return;
        }
        try(Connection c = MySQLConnection.getConnection();
            PreparedStatement statement = c.prepareStatement("SELECT * FROM tbluser WHERE username = ?")){
            //end of try
            statement.setString(1,name);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            if(resultSet.next()){
                if(password.equals(resultSet.getString("password"))){
                    USER.setUsername(resultSet.getString("username"));
                    USER.setPassword(resultSet.getString("password"));
                    USER.setUid(resultSet.getInt("uid"));
                    switchScene("main_menu.fxml");
                } else{
                    lblErrorMsg.setText("The password is incorrect");
                }
            } else{
                lblErrorMsg.setText("No such user found!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void toRegister(){
        switchScene("register.fxml");
    }
}
