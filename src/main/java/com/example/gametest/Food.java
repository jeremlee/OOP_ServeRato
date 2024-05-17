package com.example.gametest;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Food {
    String name;
    Image img;
    int key;
    Food(String name, String path, int key) throws FileNotFoundException {
        this.name = name;
        img = new Image(new FileInputStream(path));
        this.key = key;
    }
}
