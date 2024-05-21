package com.example.GameObjects;

import java.util.Random;

public class PastaRandom {
    int key;

    public PastaRandom(){
        key = 0;
    }

    public int Prand(){
        int randnum = new Random().nextInt(2)+1;
        key += randnum;
        randnum = new Random().nextInt(2)+1;
        key += randnum*10;
        randnum = new Random().nextInt(2)+1;
        key += randnum*100;
        System.out.println(key);
        return key;
    }
}
