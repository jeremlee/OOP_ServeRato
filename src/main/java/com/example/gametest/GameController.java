package com.example.gametest;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class GameController extends Controller{
    @FXML
    private Label welcomeText;
    public Button newCustomer;
    public GridPane CustomerBox;
    private Timeline timeline;

    private CustomerHandler customerHandler = new CustomerHandler();
    private Timer[] customerTimer = new Timer[customerHandler.capacity]; //need to keep track of timers to cancel timers
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    //this method will be called by application when creating new customer
    public void AddCustomer() throws FileNotFoundException {
        Image image = customerHandler.addCustomer();
        if(image == null){ //if puno na, dli mo add
            return;
        }

        VBox customerContainer = new VBox();
        Button customerSatisfied = new Button("Ordered");

        //Making Progress Bar for Patience
        ProgressBar customerPatienceBar = new ProgressBar(1);
        ImageView img = new ImageView(image);
        customerContainer.getChildren().add(customerPatienceBar);
        customerContainer.getChildren().add(img);

        customerSatisfied.setOnAction(new EventHandler<ActionEvent>(){ //proof of concept: customer can be removed anytime
            public void handle(ActionEvent actionEvent) {
                Platform.runLater(() -> RemoveCustomerContainer(customerContainer));
            }
        });
        customerContainer.getChildren().add(customerSatisfied);

        //TODO: Import Image of Customer

        //Making Start button to start Patient
        //TODO: make app start this function instead of a button DONE
        //have some function to make patience decrease faster based on customer patience
        int startDelay = 5000; // delay for 0 sec.
        int period = 1000; // repeat every sec.
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
                    Platform.runLater(() -> RemoveCustomerContainer(customerContainer));
                }
            }
        }, startDelay, period);

        CustomerBox.add(customerContainer, customerHandler.recentSeat, 0);
        customerTimer[customerHandler.recentSeat] = timer;
    }

    public void RemoveCustomerContainer(VBox customerContainer){
        int columnIndex = CustomerBox.getColumnIndex(customerContainer);
        customerHandler.removeCustomer(columnIndex);
        CustomerBox.getChildren().remove(customerContainer);
        customerTimer[columnIndex].cancel();
    }

    @Override
    public void switchScene(String fxmlFile) {
        stopTimeline();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeline = new Timeline(new KeyFrame(Duration.seconds(new Random().nextDouble(7)+3), event -> {
            try {
                AddCustomer();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    public void stopTimeline(){
        timeline.stop();
    }
}