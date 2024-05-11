package com.example.utils;

public class User {
    private String username;
    private String password;
    private static User instance;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    private int uid;

    public static User getInstance(){
        if(instance == null){
            instance = new User();
        }
        return instance;
    }
    private User(){
        username = "";
        password = "";
        uid = -1;
    }
    public String toString(){
        return uid + " " + username + " " + password;
    }
}