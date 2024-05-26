package com.example.gametest;

import com.example.gametest.utils.User;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

import static com.example.gametest.MainApplication.myOnlyStage;

public abstract class Controller implements Initializable {
    MediaPlayer bgm;
    protected User USER = User.getInstance();
    public void playMusic(String path){
        URL mediaURL = getClass().getResource(path);
        bgm = new MediaPlayer(new Media(mediaURL.toExternalForm()));
        bgm.setVolume(0.15);
        bgm.setAutoPlay(true);
        bgm.setCycleCount(MediaPlayer.INDEFINITE);
    }
    public void stopMusic(){
        bgm.stop();
    }
    public void switchScene(String fxmlFile){
        stopMusic();
        try{
            Parent p = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene s = new Scene(p);
            myOnlyStage.setScene(s);
            myOnlyStage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void toLoadingScreen(String destination){
        stopMusic();
        String loadingScreen = "loading_screen.fxml";
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(loadingScreen));
            Parent p = loader.load();
            Scene s = new Scene(p);
            LoadingScreenController controller = loader.getController();
            controller.setDestination(destination);
            myOnlyStage.setScene(s);
            myOnlyStage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void hoverIn(ImageView img){
        double targetY = img.getTranslateY() - 5;
        TranslateTransition transition = new TranslateTransition(Duration.millis(200), img);
        transition.setToY(targetY);
        transition.play();
    }
    public void hoverIn(ImageView img, int move){
        double targetY = img.getTranslateY() - move;
        TranslateTransition transition = new TranslateTransition(Duration.millis(200), img);
        transition.setToY(targetY);
        transition.play();
    }
    public void hoverOut(ImageView img){
        double targetY = img.getTranslateY() + 5;
        TranslateTransition transition = new TranslateTransition(Duration.millis(200), img);
        transition.setToY(targetY);
        transition.play();
    }
    public void hoverOut(ImageView img, int move){
        double targetY = img.getTranslateY() + move;
        TranslateTransition transition = new TranslateTransition(Duration.millis(200), img);
        transition.setToY(targetY);
        transition.play();
    }
}
