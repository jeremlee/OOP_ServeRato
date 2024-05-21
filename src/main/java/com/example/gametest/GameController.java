package com.example.gametest;

import com.example.GameObjects.Ingredient;
import com.example.GameObjects.Pasta;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

public class GameController extends Controller{
    @FXML

    public Button newCustomer;
    public GridPane CustomerBox;
    public GridPane IngredientBox;
    public Pane pnFood;
    public ImageView btnFood1;
    public Button btnToMain;
    public AnchorPane pnMenu;
    public Button btnOpenMenu;
    public Button btnCloseMenu;
    public AnchorPane pnMain;
    private Timeline timeline;
    //private MediaPlayer mediaPlayer;

    private CustomerHandler customerHandler = new CustomerHandler();
    private Timer[] customerTimer = new Timer[customerHandler.capacity]; //need to keep track of timers to cancel timers
    private Ingredient currentIngredient = new Ingredient();

    //this method will be called by application when creating new customer
    public void AddCustomer() throws FileNotFoundException {
        Customer customer = customerHandler.addCustomer();
        int seat = customerHandler.recentSeat;
        if(customer == null){ //if puno na, dli mo add
            return;
        }

        VBox customerContainer = new VBox();
        Button customerSatisfied = new Button("Ordered");

        //Making Progress Bar for Patience
        ProgressBar customerPatienceBar = new ProgressBar(1);
        ImageView img = new ImageView(customer.image);
        if(customer instanceof JeremyCustomer){
            img.getProperties().put("CustomerType","Jeremy");
        }
        if(customer instanceof KevinCustomer){
            img.getProperties().put("CustomerType","Kevin");
        }
        if(customer instanceof SeratoCustomer){
            img.getProperties().put("CustomerType","Serato");
        }

        customerContainer.getChildren().add(customer.getOrder());
        customerContainer.getChildren().add(img);
        customerContainer.getChildren().add(customerPatienceBar);

        customerSatisfied.setOnAction(new EventHandler<ActionEvent>(){ //proof of concept: customer can be removed anytime
            public void handle(ActionEvent actionEvent) {
                Platform.runLater(() -> RemoveCustomerContainer(seat));
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
                double CustomerPatience = customer.Patience * 0.1;
                if(PatientValue > 0.0){
                    double setValue = 0;
                    if(PatientValue-CustomerPatience >= 0.0){ //to handle possible negative number
                        setValue = PatientValue-CustomerPatience;
                    }

                    customerPatienceBar.setProgress(setValue);
                }else{
                    Platform.runLater(() -> RemoveCustomerContainer(seat));
                }
            }
        }, startDelay, period);

        CustomerBox.add(customerContainer, customerHandler.recentSeat, 0);
        customerTimer[customerHandler.recentSeat] = timer;
    }

    public void RemoveCustomerContainer(int columnIndex){
        customerHandler.removeCustomer(columnIndex);
        ObservableList<Node> childrens = CustomerBox.getChildren();
        for(Node node : childrens) {
            if(node instanceof VBox && CustomerBox.getRowIndex(node) == 0 && CustomerBox.getColumnIndex(node) == columnIndex) {
                VBox container = (VBox) node; // use what you want to remove
                CustomerBox.getChildren().remove(container);
                break;
            }
        }
        customerTimer[columnIndex].cancel();
    }

    public void addSpaghetti() throws FileNotFoundException {
        Pasta spag = new Pasta.PastaBuilder(50).setBase(1).setSauce(1).setTopping(1).build();;
        pnFood.getChildren().add(spag.getPastaStack());
        moveTimeline(spag);
    }

    private void moveTimeline(Pasta p) {
        int seatsize = 147;
        StackPane curSpag = p.getPastaStack();
        Timeline spaghettiTimeline = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            curSpag.setLayoutX(curSpag.getLayoutX() + 1);

            if (curSpag.getBoundsInParent().getMaxX() >= pnFood.getWidth()) {
                curSpag.setLayoutX(0);
            }

            int i = (int) curSpag.getLayoutX()/seatsize;
            if(!customerHandler.isEmpty[i] && (customerHandler.getCustomerAtSeat(i).getKey() == p.getKey())){ //I need food id && (customerHandler.getCustomerAtSeat(i).getKey() == )
                pnFood.getChildren().remove(curSpag);
                ( (Timeline) curSpag.getProperties().get("timeline")).stop();
                Platform.runLater(() -> RemoveCustomerContainer(i));

            }

        }));
        spaghettiTimeline.setCycleCount(Timeline.INDEFINITE);
        spaghettiTimeline.play();
        curSpag.getProperties().put("timeline", spaghettiTimeline);
    }

    @Override
    public void switchScene(String fxmlFile) {
        timeline.stop();
        super.switchScene(fxmlFile);
    }

    public void openMenu(){
        pnMenu.setVisible(true);
    }
    
    public void closeMenu(){
        pnMenu.setVisible(false);
    }

    public void goMainMenu(){
        switchScene("main_menu.fxml");
    }

    public void setIngredient(){
        for( Node node: IngredientBox.getChildren()) {

            if( node instanceof ImageView) {
                if( node.getBoundsInParent().contains(e.getSceneX(),  e.getSceneY())) {
                    System.out.println( "Node: " + node + " at " + GridPane.getRowIndex( node) + "/" + GridPane.getColumnIndex( node));
                }
            }
        }
    }

    public void setBase(){
        currentIngredient.setBase(0);
        System.out.println("Base set");
    }

    public void setSauce(){
        currentIngredient.setSauce(0);
        System.out.println("Base set");
    }

    public void setTopping(){
        currentIngredient.setTopping(0);//prototype design
        System.out.println("Base set");
    }


    public void ProcessIngredient() throws FileNotFoundException{
        if(currentIngredient.isEmpty()){
            System.out.println("No Ingredients!");
            return;
        }
        Pasta spag = new Pasta.PastaBuilder(50).setBase(currentIngredient.getBase()).setSauce(currentIngredient.getSauce()).setTopping(currentIngredient.getTopping()).build();;
        pnFood.getChildren().add(spag.getPastaStack());
        moveTimeline(spag);
        currentIngredient.makeEmpty();
        System.out.println("added stuff");

    }
    

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*String path = "src/main/resources/Music/bgm.mp3";
        Media sound = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();*/
        pnMenu.setStyle("-fx-background-color: #000000;");
        pnMenu.setVisible(false);
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