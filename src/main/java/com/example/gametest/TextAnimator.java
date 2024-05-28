package com.example.gametest;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class TextAnimator implements Runnable{

    private Label textArea;
    private String text;
    private int time;
    public TextAnimator(Label textArea, int time){
        this.textArea = textArea;
        this.time = time;
    }

    @Override
    public void run() {
        try{
            while(true){
                if(textArea.getText().trim().length() == 0){
                    text = "Last seconds";
                } else{
                    text = "";
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        textArea.setText(text);
                    }
                });
                Thread.sleep(time);
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
    }
}
