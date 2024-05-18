package com.example.gametest;

import java.io.FileNotFoundException;
import java.util.Random;

public class KevinCustomer extends Customer{
    KevinCustomer(int seat) throws FileNotFoundException {
        super("Kevin Atay", seat, "src/main/resources/Images/kevin.png",312);
    }
}
