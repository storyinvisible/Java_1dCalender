package com.example.trying_calendar;

public class Packages {
    String start_time;
    String end_time;
    String start_date;
    String end_date;
    String weekday;
    String community;
    Packages(String start_time,
             String end_time,
             String start_date,
             String end_date,
             String weekday,
             String community){
        this.start_date=start_date;
        this.start_time= start_time;
        this.end_date=end_date;
        this.end_time=end_time;
        this.community=community;
        this.weekday=weekday;
    }


}
