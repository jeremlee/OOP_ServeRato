package com.example.utils;

import java.util.HashMap;

public class LevelProfitMap {
    public static final HashMap<Integer, Integer> map = new HashMap<>();
    static{
        map.put(1, 600);
        map.put(2, 800);
        map.put(3, 1000);
        map.put(4, 1400);
        map.put(5, 2800);
    }
    public static HashMap<Integer, Integer> getMap() {
        return map;
    }
}
