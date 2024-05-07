package com.example.gametest;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Timer;
import java.util.TimerTask;

public class HelloController {
    @FXML
    private Label welcomeText;
    public HBox CustomerBox;
    public Button newCustomer;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    //this method will be called by application when creating new customer
    public void AddCustomer(){
        VBox customerContainer = new VBox();

        //Making Progress Bar for Patience
        ProgressBar customerPatienceBar = new ProgressBar(1);
        customerContainer.getChildren().add(customerPatienceBar);

        //Making Start button to start Patient
        //TODO: make app start this function instead of a button
        Button PatientStart = new Button();
        PatientStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //have some function to make patience decrease faster based on customer patience
                int delay = 0; // delay for 0 sec.
                int period = 1000; // repeat every sec.
                int count = 0;
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask()
                {
                    public void run()
                    {
                        double PatientValue = customerPatienceBar.getProgress();
                        customerPatienceBar.setProgress(PatientValue-0.01);
                    }
                }, delay, period);
            }
        });
        customerContainer.getChildren().add(PatientStart);
        CustomerBox.getChildren().add(customerContainer);
    }
}