package com.example.gametest;

public class Ingredient {
    int Base;
    int Sauce;
    int Topping;
    Boolean isEmpty;

    public Ingredient(){
        isEmpty = true;
        Sauce = -1;
        Base = -1;
        Topping = -1;
    }

    public void setBase(int x){
        isEmpty = false;
        Base = x;
    }

    public void setTopping(int x){
        isEmpty = false;
        Topping = x;
    }

    public void setSauce(int x){
        isEmpty = false;
        Sauce = x;
    }

    public int getBase(){
        return Base;
    }

    public int getSauce(){
        return Sauce;
    }

    public int getTopping(){
        return Topping;
    }

    public boolean isEmpty(){
        if(Sauce == -1 || Topping == -1 || Base == -1){
            return true;
        }
        return false;
    }

    public void makeEmpty(){
        Sauce = -1;
        Base = -1;
        Topping = -1;
    }
}
