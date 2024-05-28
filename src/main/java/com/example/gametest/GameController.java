package com.example.gametest;

import com.example.gametest.utils.LevelProfitMap;
import com.example.gametest.utils.LevelSpeedMap;
import com.example.gametest.utils.MySQLConnection;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class GameController extends Controller{
    @FXML
    public GridPane CustomerBox;
    public GridPane IngredientBox;
    public FlowPane IPBox;
    public Pane pnFood;
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
    public ImageView imgStopGame;
    public ImageView imgAgain;
    public ImageView imgRestart;
    public Label animatedText;
    private Timeline timeline;
    private Integer score;
    private CustomerHandler customerHandler;
    private Timer[] customerTimer; //need to keep track of timers to cancel timers
    private Ingredient currentIngredient;
    public Timeline mainTimer;
    private final int gameDuration = 60;
    private int targetScore;
    TextAnimator textAnimator;
    Thread thread = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playMusic("/com/example/gametest/Music/de_inferno.mp3");
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
        textAnimator = new TextAnimator(animatedText,100);
        timeline = new Timeline(new KeyFrame(Duration.seconds(new Random().nextDouble(5) + 2), event -> {
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
            if(remainingTimeInSeconds == 10){
                startAnim();
            }
            if (remainingTimeInSeconds <= 0) {
                animatedText.setText("Game over!");
                stopAnim();
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
                bgm.stop();
                if(score < targetScore){
                    lblStatus.setText("YOU FAILED!");
                    lblStatusMsg.setText("Not cool and normal! Please shift to IT!");
                    playMusic("/com/example/gametest/Music/fail.mp3");
                } else{
                    lblStatus.setText("YOU PASSED!");
                    lblStatusMsg.setText("Cool and normal!");
                    playMusic("/com/example/gametest/Music/success.mp3");
                }
                bgm.setCycleCount(1);
                pnGameOver.setVisible(true);
                pnFood.getChildren().clear();
                CustomerBox.getChildren().clear();
                timeline.stop();
            }
        }));
        mainTimer.setCycleCount(gameDuration);
        mainTimer.play();
    }
    public void startAnim() {
        thread = new Thread(textAnimator);
        thread.start();
    }

    public void stopAnim() {
        thread.interrupt();
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
        int startDelay = 5000; // delay for 0 sec.
        int period = 1000; // repeat every sec.
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                double PatientValue = customerPatienceBar.getProgress();
                double CustomerPatience = customer.Patience * LevelSpeedMap.getMap().get(LevelsController.LEVEL);
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

    private void moveTimeline(Pasta p) {
        int seatsize = (int) (pnFood.getWidth() / customerHandler.capacity);
        StackPane curSpag = p.getPastaStack();
        Timeline spaghettiTimeline = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            curSpag.setLayoutX(curSpag.getLayoutX() + 2);
            if (curSpag.getBoundsInParent().getMaxX() >= pnFood.getWidth()) curSpag.setLayoutX(0);
            int i = (int) curSpag.getLayoutX()/seatsize;
            if(!customerHandler.isEmpty[i] && (customerHandler.getCustomerAtSeat(i).getKey() == p.getKey())){ //I need food id && (customerHandler.getCustomerAtSeat(i).getKey() == )
                pnFood.getChildren().remove(curSpag);
                URL mediaURL = getClass().getResource("/com/example/gametest/Music/success.mp3");
                MediaPlayer sound = new MediaPlayer(new Media(mediaURL.toExternalForm()));
                sound.setVolume(0.15);
                sound.setAutoPlay(true);
                sound.setCycleCount(1);
                sound.play();
                sound.setOnEndOfMedia(() -> {
                    sound.stop();
                    sound.dispose();
                });
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
    public void onMenuHoverIn(){hoverIn(imgMenu);}
    public void onMenuHoverOut(){hoverOut(imgMenu);}
    public void onEndGameMenuHoverIn(){hoverIn(imgEndGameExit);}
    public void onEndGameMenuHoverOut(){hoverOut(imgEndGameExit);}
    public void onCloseMenuHoverIn(){hoverIn(imgCloseMenu);}
    public void onCloseMenuHoverOut(){hoverOut(imgCloseMenu);}
    public void onStopHoverIn(){hoverIn(imgStopGame);}
    public void onStopHoverOut(){hoverOut(imgStopGame);}
    public void playAgain(){toLoadingScreen("game.fxml");}
    public void onAgainHoverIn(){hoverIn(imgAgain);}
    public void onAgainHoverOut(){hoverOut(imgAgain);}
    public void onRestartHoverIn(){hoverIn(imgRestart);}
    public void onRestartHoverOut(){hoverOut(imgRestart);}
    public void setIngredient(MouseEvent e){
        int row = -1;
        int col = -1;
        int cmp = 0;
        for(Node node: IngredientBox.getChildren()) {
            if(node instanceof ImageView) {
                if((node.getLayoutX() + IngredientBox.getLayoutX()) < e.getSceneX() && cmp == 0) col++;
                cmp++;
                cmp%=3;
            }
        }
        for(Node node: IngredientBox.getChildren()) {
            if(node instanceof ImageView) {
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
        if(currentIngredient.isEmpty()) return; //no ingredients
        Pasta spag = new Pasta.PastaBuilder(50).setBase(currentIngredient.getBase()).setSauce(currentIngredient.getSauce()).setTopping(currentIngredient.getTopping()).build();
        pnFood.getChildren().add(spag.getPastaStack());
        moveTimeline(spag);
        currentIngredient.makeEmpty();
        IPBox.getChildren().clear();
    }
    private void updateScore(){
        score+=200;
        txtScore.setText(score.toString());
    }
    public void toLoadingScreen(String fxmlFile){
        if(thread != null) thread.interrupt();
        mainTimer.stop();
        timeline.stop();
        super.toLoadingScreen(fxmlFile);
    }
    public void endGame(){toLoadingScreen("main_menu.fxml");}

}