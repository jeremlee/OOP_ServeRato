package com.example.gametest;

import java.io.FileNotFoundException;

public class SeratoCustomer extends Customer{
    SeratoCustomer(int seat) throws FileNotFoundException {
        super("Jay Vince Serato", seat, "src/main/resources/com/example/gametest/characters/serato1.png",111);
    }
}
