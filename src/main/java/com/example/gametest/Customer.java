package com.example.gametest;

import com.example.GameObjects.Pasta;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Customer {
    String name;
    int Patience;
    int seatNumber;
    Image image;
    StackPane PastaOrder;
    int key;

    Customer(String name, int seat, String imgPath, int key) throws FileNotFoundException {
        this.name = name;
        this.Patience = 1;
        this.seatNumber = seat;
        this.key = key;
        image = new Image(new FileInputStream(imgPath));

        Pasta p = new Pasta.PastaBuilder(200).setBase((key/100)-1).setSauce(((key/10)%10)-1).setTopping((key%10)-1).build();
        PastaOrder = p.getPastaStack();
    }

    public void setKey(int key){
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public StackPane getOrder(){
        return PastaOrder;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", Patience=" + Patience +
                ", seatNumber=" + seatNumber +
                '}';
    }
}

