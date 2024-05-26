package com.example.gametest.utils;

import java.util.HashMap;

public class LevelDescriptionMap {
    public static final HashMap<Integer, String> map = new HashMap<>();
    static{
        map.put(1,
                "Welcome to the 1st level.\n\nToday is your first day in DAA Laboratory and you must " +
                        "serve sir Serato in order to survive DAA!");
        map.put(2, "Welcome to the 2nd level.\n\nYou are now approaching prelim exams and you must " +
                "sell more food in order to survive the prelim exams of Sir Serato!");
        map.put(3, "Welcome to the 3rd level.\n\nYou are now approaching midterms and you must " +
                "sell even more food and be faster in order to calculate " +
                "the runtime of recursive algorithms!");
        map.put(4, "Welcome to the 4th level.\n\nYou are now approaching pre-finals and you must sell " +
                "a lot more food than you did in order to learn hash tables and self-balancing " +
                "trees!");
        map.put(5, "Welcome to the final level.\n\nYou are now approaching finals and you must sell like" +
                " there is no tomorrow or else you will fail the DAA practical exams!");
    }
    public static HashMap<Integer, String> getMap() {
        return map;
    }
}
