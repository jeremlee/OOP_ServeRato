package com.example.gametest.utils;

import java.util.HashMap;

public class LevelProfitMap {
    public static final HashMap<Integer, Integer> map = new HashMap<>();
    static{
        map.put(1, 1000);
        map.put(2, 2000);
        map.put(3, 3000);
        map.put(4, 4000);
        map.put(5, 5000);
    }
    public static HashMap<Integer, Integer> getMap() {
        return map;
    }
}
