package com.example.GameObjects;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class PastaHandler {
    ArrayList<Pasta> Pastas;

    public PastaHandler(){
        Pastas = new ArrayList<>();
    }

//    public void addPasta(int Base, int Sauce, int Topping){
//        try{
//            Pasta p = new Pasta.PastaBuilder(60).setBase(Base).setSauce(Sauce).setTopping(Topping).build();
//            Pastas.add(p);
//        }catch (FileNotFoundException e){
//            e.printStackTrace();
//        }
//    }
}
