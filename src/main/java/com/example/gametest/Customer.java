package com.example.gametest;

public class Customer {
    String name;
    int Patience;
    int seatNumber;

    Customer(String name, int seat){
        this.name = name;
        this.Patience = 1;
        this.seatNumber = seat;
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

