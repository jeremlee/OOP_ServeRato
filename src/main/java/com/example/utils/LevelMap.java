package com.example.utils;

import java.util.HashMap;

public class LevelMap {
    public static final HashMap<Integer, Double> map = new HashMap<>();
    static{
        map.put(1, 0.1);
        map.put(2, 0.15);
        map.put(3, 0.2);
        map.put(4, 0.25);
        map.put(5, 0.4);
    }
    public static HashMap<Integer, Double> getMap() {
        return map;
    }
}