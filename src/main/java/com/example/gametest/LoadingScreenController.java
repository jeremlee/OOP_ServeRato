package com.example.gametest;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class LoadingScreenController extends Controller{
    public ImageView img1;
    public ImageView img2;
    public ImageView img3;
    public ImageView img4;
    public ImageView img5;
    private String destination;
    Timeline timeline;
    public void setDestination(String destination){
        this.destination = destination;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeline = new Timeline(
                new KeyFrame(Duration.ZERO, (ActionEvent event) -> {
                    hoverIn(img1,30);
                }),
                new KeyFrame(Duration.seconds(0.5), (ActionEvent event) -> {
                    hoverOut(img1,30);
                    hoverIn(img2,30);
                }),
                new KeyFrame(Duration.seconds(1), (ActionEvent event) -> {
                    hoverOut(img2,30);
                    hoverIn(img3,30);
                }),
                new KeyFrame(Duration.seconds(1.5), (ActionEvent event) -> {
                    hoverOut(img3,30);
                    hoverIn(img4,30);
                }),
                new KeyFrame(Duration.seconds(2), (ActionEvent event) -> {
                    hoverOut(img4,30);
                    hoverIn(img5,30);
                }),
                new KeyFrame(Duration.seconds(2.5), (ActionEvent event) -> {
                    hoverOut(img5,30);
                }),
                new KeyFrame(Duration.seconds(3), (ActionEvent event) -> {
                    switchScene(destination);
                    stopTimeline();
                })
        );
        timeline.play();
    }

    public void stopTimeline(){ timeline.stop();}
}
