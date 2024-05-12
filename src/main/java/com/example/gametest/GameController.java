package com.example.gametest;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Timer;
import java.util.TimerTask;

public class GameController extends Controller{
    @FXML
    private Label welcomeText;
    public Button newCustomer;
    public GridPane CustomerBox;

    private CustomerHandler customerHandler = new CustomerHandler();
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    //this method will be called by application when creating new customer
    public void AddCustomer(){
        if(!customerHandler.addCustomer()){ //if puno na, dli mo add
            return;
        }


        VBox customerContainer = new VBox();

        //Making Progress Bar for Patience
        ProgressBar customerPatienceBar = new ProgressBar(1);
        customerContainer.getChildren().add(customerPatienceBar);

        //TODO: Import Image of Customer

        //Making Start button to start Patient
        //TODO: make app start this function instead of a button DONE
        //have some function to make patience decrease faster based on customer patience
        int startDelay = 5000; // delay for 0 sec.
        int period = 1000; // repeat every sec.
        int count = 0;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            public void run()
            {
                double PatientValue = customerPatienceBar.getProgress();
                double CustomerPatience = 0.1;
                if(PatientValue > 0.0){
                    double setValue = 0;
                    if(PatientValue-CustomerPatience >= 0.0){ //to handle possible negative number
                        setValue = PatientValue-CustomerPatience;
                    }


                    customerPatienceBar.setProgress(setValue);
                }else{
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            customerHandler.removeCustomer(CustomerBox.getColumnIndex(customerContainer));
                            CustomerBox.getChildren().remove(customerContainer);
                        }
                    });
                    timer.cancel();
                }
            }
        }, startDelay, period);

        CustomerBox.add(customerContainer, customerHandler.recentSeat, 0);
    }
}