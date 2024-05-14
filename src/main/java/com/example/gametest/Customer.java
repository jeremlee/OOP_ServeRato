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

    Customer(String name, int seat, String imgPath) throws FileNotFoundException {
        this.name = name;
        this.Patience = 1;
        this.seatNumber = seat;
        image = new Image(new FileInputStream(imgPath));
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

