package com.example.gametest;

import java.util.Random;

public class PastaRandom {
    int key;
    Random rand;

    public PastaRandom(){
        key = 0;
        rand = new Random();
    }

    public int Prand(){
        int randnum = rand.nextInt(2)+1;
        key += randnum;
        randnum = rand.nextInt(2)+1;
        key += randnum*10;
        randnum = rand.nextInt(2)+1;
        key += randnum*100;
        System.out.println(key);
        return key;
    }
}
