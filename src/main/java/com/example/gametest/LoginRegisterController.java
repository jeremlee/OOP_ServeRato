package com.example.gametest;

import com.example.utils.MySQLConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.gametest.MainApplication.myOnlyStage;

public class LoginRegisterController extends Controller {
    public Button btnLogin;
    public Button btnRegister;
    public TextField txtfUsername;
    public PasswordField txtfPassword;
    public Label lblErrorMsg;

    public void insertData(String name, String password){
        try(Connection c = MySQLConnection.getConnection();
            PreparedStatement statement = c.prepareStatement("INSERT INTO tbluser(username,password) VALUES (?,?)")){
            //end of try
            c.setAutoCommit(false);
            statement.setString(1,name);
            statement.setString(2,password);
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected > 0){
                lblErrorMsg.setText("Successful registration, please login!");
                txtfUsername.setText("");
                txtfPassword.setText("");
            }
            c.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean readData(String name, String password){
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
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void onLoginClick(){
        if(readData(txtfUsername.getText(),txtfPassword.getText())){
            lblErrorMsg.setText("");
            System.out.println(USER);
            //mo-adto nis lain scene

            try{
                Parent p = FXMLLoader.load(getClass().getResource("game.fxml")); //throws IOException
                Scene s = new Scene(p);
                myOnlyStage.setScene(s);
                myOnlyStage.show();
            }catch(IOException e){
                e.printStackTrace();
            }

        } else{
            lblErrorMsg.setText("Incorrect username/password!");
        }
    }
    @FXML
    public void onRegisterClick(){
        insertData(txtfUsername.getText(), txtfPassword.getText());
    }

}
