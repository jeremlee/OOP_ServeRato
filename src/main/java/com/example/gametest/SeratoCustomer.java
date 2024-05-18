package com.example.gametest;

import java.io.FileNotFoundException;

public class SeratoCustomer extends Customer{
    SeratoCustomer(int seat) throws FileNotFoundException {
        super("Jay Vince Serato", seat, "src/main/resources/Images/serato1.png",111);
    }
}
