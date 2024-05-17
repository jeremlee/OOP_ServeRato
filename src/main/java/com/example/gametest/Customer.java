package com.example.gametest;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Customer {
    String name;
    int Patience;
    int seatNumber;
    Image image;
    int key;

    Customer(String name, int seat, String imgPath, int key) throws FileNotFoundException {
        this.name = name;
        this.Patience = 1;
        this.seatNumber = seat;
        this.key = key;
        image = new Image(new FileInputStream(imgPath));
    }

    public int getKey() {
        return key;
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

