package com.example.trying_calendar;

public class Listed_user {
    private String username;
    private String desc;
    public Listed_user(String username, String desc) {
        this.username = username;
        this.desc = desc;
    }

    public String getUsername() {
        return username;
    }

    public String getDesc() {
        return desc;
    }
}
