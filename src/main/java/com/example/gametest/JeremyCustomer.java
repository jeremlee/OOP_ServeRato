package com.example.gametest;

import java.io.FileNotFoundException;

public class JeremyCustomer extends Customer{
    JeremyCustomer(int seat) throws FileNotFoundException {
        super("Jeremy Lee", seat, "src/main/resources/com/example/gametest/characters/serato2.png",222);
    }

}
