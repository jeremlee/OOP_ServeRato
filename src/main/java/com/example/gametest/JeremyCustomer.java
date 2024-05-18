package com.example.gametest;

import java.io.FileNotFoundException;

public class JeremyCustomer extends Customer{
    JeremyCustomer(int seat) throws FileNotFoundException {
        super("Jeremy Lee", seat, "src/main/resources/Images/ako.png",222);
    }


}
