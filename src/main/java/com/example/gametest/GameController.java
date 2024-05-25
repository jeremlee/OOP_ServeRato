package com.example.gametest;

import com.example.utils.LevelProfitMap;
import com.example.utils.LevelSpeedMap;
import com.example.utils.MySQLConnection;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class GameController extends Controller{
    @FXML
    public Button newCustomer;
    public GridPane CustomerBox;
    public GridPane IngredientBox;
    public FlowPane IPBox;
    public Pane pnFood;
    public ImageView btnFood1;
    public AnchorPane pnMenu;
    public AnchorPane pnMain;
    public Label txtScore;
    public Label timerLabel;
    public ImageView imgMenu;
    public Label lblLevel;
    public ImageView imgCloseMenu;
    public Label lblStatus;
    public Label lblStatusMsg;
    public ImageView imgEndGameExit;
    public AnchorPane pnGameOver;
    private Timeline timeline;
    private Integer score;
    //private MediaPlayer mediaPlayer;
    private CustomerHandler customerHandler;
    private Timer[] customerTimer; //need to keep track of timers to cancel timers
    private Ingredient currentIngredient;
    public Timeline mainTimer;
    private final int gameDuration = 60;
    private int targetScore;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*String path = "src/main/resources/Music/bgm.mp3";
        Media sound = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();*/
        HashMap<Integer, Integer> lvlProfitMap = LevelProfitMap.getMap();
        targetScore = lvlProfitMap.get(LevelsController.LEVEL);
        customerHandler = new CustomerHandler();
        customerTimer = new Timer[customerHandler.capacity];
        currentIngredient = new Ingredient();
        score = 0;
        txtScore.setText("0");
        lblLevel.setText(LevelsController.LEVEL.toString());
        lblStatus.setWrapText(true);
        lblStatusMsg.setWrapText(true);
        pnMenu.setVisible(false);
        pnGameOver.setVisible(false);
        timeline = new Timeline(new KeyFrame(Duration.seconds(new Random().nextDouble(4) + 1), event -> {
            try {
                AddCustomer();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        int initialMinutes = gameDuration / 60;
        int initialSeconds = gameDuration % 60;
        timerLabel.setText(String.format("%02d:%02d", initialMinutes, initialSeconds));
        final int[] elapsedTime = {0};
        mainTimer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            elapsedTime[0]++;
            int remainingTimeInSeconds = gameDuration - elapsedTime[0];
            int minutes = remainingTimeInSeconds / 60;
            int seconds = remainingTimeInSeconds % 60;
            timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
            if (remainingTimeInSeconds <= 0) {
                try(Connection c = MySQLConnection.getConnection();
                    PreparedStatement statement = c.prepareStatement("INSERT INTO tblgame(uid,score,level) VALUES(?,?,?)"))  {
                    //start of try
                    c.setAutoCommit(false);
                    statement.setInt(1,USER.getUid());
                    statement.setInt(2,score);
                    statement.setInt(3,LevelsController.LEVEL);
                    statement.executeUpdate();
                    c.commit();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                //end game
                if(score < targetScore){
                    lblStatus.setText("YOU FAILED!");
                    lblStatusMsg.setText("Not cool and normal! Please shift to IT!");
                } else{
                    lblStatus.setText("YOU LIVE TO SEE ANOTHER DAY!");
                    lblStatusMsg.setText("Cool and normal!");
                }
                pnGameOver.setVisible(true);
                pnFood.getChildren().clear();
                CustomerBox.getChildren().clear();
                timeline.stop();
            }
        }));
        mainTimer.setCycleCount(gameDuration);
        mainTimer.play();
    }
    public void AddCustomer() throws FileNotFoundException { //this method will be called by application when creating new customer
        Customer customer = customerHandler.addCustomer();
        int seat = customerHandler.recentSeat;
        if(customer == null) return; //means puno na
        VBox customerContainer = new VBox();
        customerContainer.setSpacing(10);
        ProgressBar customerPatienceBar = new ProgressBar(1); //Making Progress Bar for Patience
        ImageView img = new ImageView(customer.image);
        StackPane order = customer.getOrder();
        customerContainer.getChildren().addAll(order, img, customerPatienceBar);
        //have some function to make patience decrease faster based on customer patience
        int startDelay = 5000; // delay for 0 sec.
        int period = 1000; // repeat every sec.
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                double PatientValue = customerPatienceBar.getProgress();
                double CustomerPatience = customer.Patience * LevelSpeedMap.getMap().get(LevelsController.LEVEL);
                //customer patience is based on level
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
        Pasta spag = new Pasta.PastaBuilder(50).setBase(1).setSauce(1).setTopping(1).build();
        pnFood.getChildren().add(spag.getPastaStack());
        moveTimeline(spag);
    }

    private void moveTimeline(Pasta p) {
        int seatsize = (int) (pnFood.getWidth() / customerHandler.capacity);
        StackPane curSpag = p.getPastaStack();
        Timeline spaghettiTimeline = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            curSpag.setLayoutX(curSpag.getLayoutX() + 1);
            if (curSpag.getBoundsInParent().getMaxX() >= pnFood.getWidth()) {
                curSpag.setLayoutX(0);
            }
            int i = (int) curSpag.getLayoutX()/seatsize;
            if(!customerHandler.isEmpty[i] && (customerHandler.getCustomerAtSeat(i).getKey() == p.getKey())){ //I need food id && (customerHandler.getCustomerAtSeat(i).getKey() == )
                pnFood.getChildren().remove(curSpag);
                ((Timeline) curSpag.getProperties().get("timeline")).stop();
                Platform.runLater(() -> RemoveCustomerContainer(i));
                updateScore();
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
    public void openMenu(){pnMenu.setVisible(true);}
    public void onCloseMenu(){pnMenu.setVisible(false);}
    public void goMainMenu(){switchScene("main_menu.fxml");}
    public void setIngredient(MouseEvent e){
        int row = -1;
        int col = -1;
        int cmp = 0;
        System.out.println("Test: " + e.getSceneX() + " " + e.getSceneY());
        for(Node node: IngredientBox.getChildren()) {
            if(node instanceof ImageView) {
                System.out.println("Node: "+ (node.getLayoutX() + IngredientBox.getLayoutX()) + " " +  (node.getLayoutY() + IngredientBox.getLayoutY()));
                if((node.getLayoutX() + IngredientBox.getLayoutX()) < e.getSceneX() && cmp == 0){
                    col++;
                }
                cmp++;
                cmp%=3;
            }
        }
        for(Node node: IngredientBox.getChildren()) {
            if( node instanceof ImageView) {
                System.out.println("Node: "+ (node.getLayoutX() + IngredientBox.getLayoutX()) + " " +  (node.getLayoutY() + IngredientBox.getLayoutY()));
                if((node.getLayoutY() + IngredientBox.getLayoutY()) < e.getSceneY() && row<2){
                    row++;
                }else{
                    break;
                }
            }
        }
        ImageView reference = (ImageView) IngredientBox.getChildren().get((col*3)+row);
        ImageView ing = new ImageView();
        ing.setImage(reference.getImage());
        ing.setFitHeight(50);
        ing.setFitWidth(50);
        switch(col){
            case 0:
                currentIngredient.setBase(row);
                break;
            case 1:
                currentIngredient.setSauce(row);
                break;
            case 2:
                currentIngredient.setTopping(row);
                break;
        }
        IPBox.getChildren().add(ing);
    }
    public void setBase(){ currentIngredient.setBase(0);}
    public void setSauce(){ currentIngredient.setSauce(0);}
    public void setTopping(){ currentIngredient.setTopping(0);}//prototype design
    public void ProcessIngredient() throws FileNotFoundException{
        if(currentIngredient.isEmpty()){
            System.out.println("No Ingredients!");
            return;
        }
        Pasta spag = new Pasta.PastaBuilder(50).setBase(currentIngredient.getBase()).setSauce(currentIngredient.getSauce()).setTopping(currentIngredient.getTopping()).build();
        pnFood.getChildren().add(spag.getPastaStack());
        moveTimeline(spag);
        currentIngredient.makeEmpty();
        System.out.println("added stuff");
        IPBox.getChildren().clear();
    }
    private void updateScore(){
        score+=200;
        txtScore.setText(score.toString());
    }
    public void endGame(){
        toLoadingScreen("main_menu.fxml");
    }
}