package com.example.trying_calendar;

public class Events {
    String date_from;
    String date_to;
    String details;
    String time_from;
    String time_to;
    String Descrirption;
    Events(String date_from,
            String date_to,
            String details,
            String time_from,
            String time_to,
           String Description){
        this.date_from=date_from;
        this.date_to=date_to;
        this.details=details;
        this.time_from=time_from;
        this.time_to= time_to;
        this.Descrirption= Description;

    }
}
