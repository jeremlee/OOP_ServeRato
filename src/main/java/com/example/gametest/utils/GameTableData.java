package com.example.gametest.utils;

import java.util.Date;

public class GameTableData {
    private String name;
    private int lvl;
    private int score;
    private Date time;
    public GameTableData(String username, int score, int lvl, Date timePlayed){
        this.name = username;
        this.lvl = lvl;
        this.score = score;
        this.time = timePlayed;
    }
    public String getName() {return name;}
    public int getLvl() {return lvl;}
    public int getScore() {return score;}
    public Date getTime() {return time;}
}
