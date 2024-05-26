package com.example.gametest;

import java.io.FileNotFoundException;

public class KevinCustomer extends Customer{
    KevinCustomer(int seat) throws FileNotFoundException {
        super("Kevin Atay", seat, "src/main/resources/com/example/gametest/characters/seratokid.png");
    }
}
