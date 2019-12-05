package com.example.trying_calendar;

import java.util.HashMap;

public class Events_details {
    String date_from;

    String name;
    String time_from;
    String time_to;
    String Description;
    boolean RSVP;
    Events_details(String name,
                   String date_from,
                   String time_from,
                   String time_to,
                   String Description,boolean RSVP){
        this.date_from=date_from;

        this.name=name;
        this.time_from=time_from;
        this.time_to= time_to;
        this.Description= Description;
        this.RSVP=RSVP;

    }

    public String getDate_from() {
        return date_from;
    }

    public String getDescription() {
        return Description;
    }

    public String getName() {
        return name;
    }

    public String getTime_from() {
        return time_from;
    }

    public String getTime_to() {
        return time_to;
    }
    public HashMap<String,String> getHashmao(){
        HashMap<String,String> eventHashmap = new HashMap<String ,String>();
        eventHashmap.put("date_from",date_from);
        eventHashmap.put("time_from",time_from);
        eventHashmap.put("time_to",time_to);
        eventHashmap.put("details",Description);
        return eventHashmap;


    }

}
