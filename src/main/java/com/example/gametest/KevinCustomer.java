package com.example.gametest;

import com.example.GameObjects.PastaRandom;

import java.io.FileNotFoundException;

public class KevinCustomer extends Customer{
    KevinCustomer(int seat) throws FileNotFoundException {
        super("Kevin Atay", seat, "src/main/resources/Images/kevin.png");
    }
}
