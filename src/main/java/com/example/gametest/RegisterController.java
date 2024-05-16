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

public class RegisterController extends Controller{
    public PasswordField txtfPassword;
    public TextField txtfUsername;
    public Button btnRegister;
    public Label lblErrorMsg;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    public void onRegisterClick(){
        insertData(txtfUsername.getText(), txtfPassword.getText());
    }
    public void onRegisterEnter(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER){
            insertData(txtfUsername.getText(), txtfPassword.getText());
        }
    }

    public void toLogin(){
        switchScene("login.fxml");
    }
    public void insertData(String name, String password){
        if(name.isBlank() || password.isBlank()){
            lblErrorMsg.setText("Please fill up the fields.");
            return;
        }
        try(Connection c = MySQLConnection.getConnection();
            PreparedStatement checker = c.prepareStatement("SELECT COUNT(*) FROM tbluser WHERE username = ?");
            PreparedStatement statement = c.prepareStatement("INSERT INTO tbluser(username,password) VALUES (?,?)")){
            //end of try
            c.setAutoCommit(false);
            int count = 0;
            checker.setString(1,name);
            checker.executeQuery();
            ResultSet rs = checker.getResultSet();
            if(rs.next()){
                count = rs.getInt(1);
            }
            if(count == 0){
                statement.setString(1,name);
                statement.setString(2,password);
                statement.executeUpdate();
                lblErrorMsg.setText("Successful registration, please login!");
                txtfUsername.setText("");
                txtfPassword.setText("");
            } else{
                lblErrorMsg.setText("This username is already in use!");
            }
            c.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
