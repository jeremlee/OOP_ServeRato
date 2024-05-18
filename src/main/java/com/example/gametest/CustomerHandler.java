package com.example.gametest;

import javafx.scene.image.Image;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class CustomerHandler {
    static ArrayList<Customer> Customers;
    static boolean[] isEmpty;
    int id = 0; //para unique ang customername...idk
    int recentSeat;
    int capacity;

    CustomerHandler(){
        capacity = 6;
        Customers = new ArrayList<Customer>();
        isEmpty = new boolean[capacity];
        Arrays.fill(isEmpty, true);
    }

    public Customer addCustomer() throws FileNotFoundException { //returns true, if there is still seats. otherwise false
        if(Customers.size() == capacity){
            System.out.println("Puno na");
            return null;
        }
        Customer newCustomer;
        Random random = new Random();
        int num = random.nextInt(3);
        int seat = checkSeat();
        switch(num){
            case 0:
                newCustomer = new KevinCustomer(seat);
                break;
            case 1:
                newCustomer = new SeratoCustomer(seat);
                break;
            case 2:
                newCustomer = new JeremyCustomer(seat);
                break;
            default:
                newCustomer = new Customer("Who?",seat,"src/main/resources/Images/lowresOsaker.png",4);
                break;
        }
        Customers.add(newCustomer);
        recentSeat = seat;
        id++;
        printCustomers();
        return newCustomer;
    }

    public Customer getCustomerAtSeat(int seat){
        Customer seatedCustomer = null;

        for(Customer c: Customers){
            if(c.seatNumber == seat){
                seatedCustomer = c;
                break;
            }
        }

        return seatedCustomer;
    }

    public void removeCustomer(int seat){
        for(Customer c : Customers){
            System.out.println("test");
            if(c.seatNumber == seat){
                Customers.remove(c);
                break;
            }
        }
        isEmpty[seat] = true;
        printCustomers();
    }

    public int checkSeat(){
        Random rand = new Random();
        int seat = rand.nextInt(capacity-1);
        while(!isEmpty[seat%capacity]){
            System.out.println("Checking Seat #" + seat%capacity);
            seat++;
        }
        isEmpty[seat%capacity] = false;
        System.out.println("New Cusomter at #" + seat%capacity);
        return seat%capacity;
    }

    public void printCustomers(){
        System.out.println("All Customers: ");
        for(Customer c : Customers){
            System.out.println(c);
        }
    }
}

