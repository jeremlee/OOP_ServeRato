package com.example.gametest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class CustomerHandler {
    ArrayList<Customer> Customers;
    boolean[] isEmpty;
    int id = 0; //para unique ang customername...idk
    int recentSeat;
    int capacity;

    CustomerHandler(){
        capacity = 6;
        Customers = new ArrayList<Customer>();
        isEmpty = new boolean[capacity];
        Arrays.fill(isEmpty, true);
    }

    public boolean addCustomer(){ //returns true, if there is still seats. otherwise false
        if(Customers.size() == capacity){
            System.out.println("Puno na");
            return false;
        }

        String name =  "Customer";
        int seat = checkSeat();
        Customer newCustomer = new Customer(name+ id, seat);
        Customers.add(newCustomer);
        recentSeat = seat;
        id++;
        printCustomers();
        return true;
    }

    public void removeCustomer(int seat){
        int index = 0;
        for(Customer c : Customers){
            if(c.seatNumber == seat){
                break;
            }
            index++;
        }
        isEmpty[seat] = true;
        Customers.remove(index);
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

