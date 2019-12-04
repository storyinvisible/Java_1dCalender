package com.example.trying_calendar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.alamkanak.weekview.WeekViewLoader;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CalendarActivity extends Base_Calendar  {
    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_SEVEN_DAY_VIEW = 5;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_SEVEN_DAY_VIEW;
    private WeekView mWeekView;
    FirebaseAuth firebaseAuth;
    DatabaseReference mUserEventReference;
    HashMap <String, WeekViewEvent> eventHashMap= new HashMap<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user_firebase = firebaseAuth.getCurrentUser();
        String email = user_firebase.getEmail().toString();
        String user = new RegistrationActivity().emailToName(email);
        mUserEventReference = FirebaseDatabase.getInstance().getReference("User"+"/"+user+"/"+"event");
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        //highlight menu items when clicked
        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        mWeekView = (WeekView) findViewById(R.id.weekView);
        update_events();
        mWeekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
            @Override
            public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
                for(Map.Entry<String, WeekViewEvent> entry: eventHashMap.entrySet()){
                    if (eventMatches(entry.getValue(), newYear, newMonth)) {
                        events.add(entry.getValue()) ;
                    }
                }
                return events;
            }
        });

        // Show a toast message about the touched event.
        mWeekView.setOnEventClickListener(this);
        mWeekView.setNumberOfVisibleDays(5);
        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);

        // Set up a date time interpreter to interpret how the date and time will be formatted in
        // the week view. This is optional.
        setupDateTimeInterpreter(false);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.newsfeed:
                        Intent intent1 = new Intent(CalendarActivity.this, NewsFeedActivity.class);
                        startActivity(intent1);
                        return true;
                    case R.id.calendar:
                        Intent intent2 = new Intent(CalendarActivity.this, CalendarActivity.class);
                        startActivity(intent2);
                        return true;
                    case R.id.packages:
                        Intent intent3 = new Intent(CalendarActivity.this, PackageActivity.class);
                        startActivity(intent3);
                        return true;
                }
                return false;
            }
        });
    }


    public void update_events() {

        mUserEventReference.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange (@NonNull DataSnapshot dataSnapshot){
                HashMap<String, Object> all_event = new HashMap<>();

                for (DataSnapshot event : dataSnapshot.getChildren()) {
                    all_event.put(event.getKey(), event.getValue());
                }
            Log.i("hashmap", all_event.toString());
            for (Map.Entry<String, Object> entry : all_event.entrySet()) {
                String event_name = entry.getKey();
                HashMap<String, Object> one_event_with_details = (HashMap<String, Object>) entry.getValue();
                Log.i("hash value", one_event_with_details.toString());


                String start_date_str = one_event_with_details.get("date_from").toString();
                String end_date_str = one_event_with_details.get("date_to").toString();
                String start_time_str = one_event_with_details.get("time_from").toString();
                String end_time_str = one_event_with_details.get("time_to").toString();
                String start_date = start_date_str + " " + start_time_str;
                end_date_str = start_date_str + " " + end_time_str;
                Calendar calendar_start = CalendarActivity.this.get_calendar(start_date);
                Calendar calendar_end = get_calendar(end_date_str);
                Calendar startTime = Calendar.getInstance();
                Date date = calendar_start.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
                String strDate = dateFormat.format(date);
                Log.i("The calendar event date",strDate);
                WeekViewEvent single_event = new WeekViewEvent(1, entry.getKey() , calendar_start,calendar_end);
                Log.i("The event details",single_event.toString());
                if(eventHashMap.get(entry.getKey())==null) {
                    eventHashMap.put(entry.getKey(), single_event);
                }
                if (one_event_with_details != null) {
                    Toast.makeText(CalendarActivity.this, "every thing is good!!!!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(CalendarActivity.this, "HashMap is good!!!", Toast.LENGTH_SHORT).show();
                }


            }
            mWeekView.notifyDatasetChanged();

        }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    //take event out and put in canlender

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        // Populate the week view with some events.

        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        for(Map.Entry<String, WeekViewEvent> entry: eventHashMap.entrySet()){
            if (eventMatches(entry.getValue(), newYear, newMonth)) {
                events.add(entry.getValue()) ;
            }
        }
        return events;
        //AllDay event


    }
    public Calendar get_calendar(String datestring){
        Calendar cal = Calendar.getInstance();
        Log.i("The datetime string", datestring);
        try {
            Date date1 = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(datestring);

            cal.setTime(date1);
        }
        catch (Exception e){
            System.out.println("Error occurs when set time ");
        }
        return cal;
    }
    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        super.onEventLongPress(event, eventRect);
        Log.i("CAl", "try to view details from weekview");
        Intent intent = new Intent(CalendarActivity.this, View_Event_Details.class);
        startActivity(intent);
    }

    @SuppressLint("DefaultLocale")
    protected String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY),
                time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onEmptyViewClicked(Calendar time) {
        super.onEmptyViewClicked(time);
        Intent intent = new Intent(CalendarActivity.this, Create_Event.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.o_create_packages:
                Intent create_packages = new Intent(CalendarActivity.this, Create_packages.class);
                startActivity(create_packages);
                return true;
            case R.id.o_create_event:
                Intent create_event = new Intent(CalendarActivity.this,Create_Event.class);
                startActivity(create_event);
                return true;
            case R.id.o_settings:
                Toast.makeText(this, "Go To Settings", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.o_logout:
                Intent intent = new Intent(CalendarActivity.this, Login_Activity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private boolean eventMatches(WeekViewEvent event, int year, int month) {
        return (event.getStartTime().get(Calendar.YEAR) == year && event.getStartTime().get(Calendar.MONTH) == month - 1) || (event.getEndTime().get(Calendar.YEAR) == year && event.getEndTime().get(Calendar.MONTH) == month - 1);
    }
}



