package com.example.gametest;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class GameController extends Controller{
    @FXML

    public Button newCustomer;
    public GridPane CustomerBox;
    public Pane pnFood;
    public ImageView btnFood1;
    private Timeline timeline;
    //private MediaPlayer mediaPlayer;


    private CustomerHandler customerHandler = new CustomerHandler();
    private Timer[] customerTimer = new Timer[customerHandler.capacity]; //need to keep track of timers to cancel timers
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

    public void addSpaghetti() throws FileNotFoundException {
        Spaghetti spag = new Spaghetti();
        ImageView img = new ImageView(spag.img);
        img.setFitWidth(50);
        img.setFitHeight(50);
        pnFood.getChildren().add(img);
        moveTimeline(img);
    }
    private void moveTimeline(ImageView img) {
        Timeline spaghettiTimeline = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            img.setLayoutX(img.getLayoutX() + 1);
            if (img.getBoundsInParent().getMaxX() >= pnFood.getWidth()) {
                pnFood.getChildren().remove(img);
                ((Timeline) img.getProperties().get("timeline")).stop();
            }
        }));
        spaghettiTimeline.setCycleCount(Timeline.INDEFINITE);
        spaghettiTimeline.play();
        img.getProperties().put("timeline", spaghettiTimeline);
    }

    @Override
    public void switchScene(String fxmlFile) {
        timeline.stop();
        //mediaPlayer.stop();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*String path = "src/main/resources/Music/bgm.mp3";
        Media sound = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();*/
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

}