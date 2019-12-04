package com.example.trying_calendar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.alamkanak.weekview.WeekViewEvent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
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
import java.util.Map;

public class NewsFeedActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference onSchedule_Events;
    CardView Event_box;
    String current_user;
    ArrayList<Events> event_lists=  new ArrayList<>();
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);

        TextView text = findViewById(R.id.textView5);
        text.setText("This is the News Feed!");
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user_firebase = firebaseAuth.getCurrentUser();
        if(user_firebase!=null){
            String email = user_firebase.getEmail().toString();
            current_user = new RegistrationActivity().emailToName(email);
        }
        final DatabaseReference Events= database.getReference(current_user).child("event");
        Events.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        //highlight menu items when clicked
        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);


        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.newsfeed:
                        Intent intent1 = new Intent(NewsFeedActivity.this, NewsFeedActivity.class);
                        startActivity(intent1);
                        return true;
                    case R.id.calendar:
                        Intent intent2 = new Intent(NewsFeedActivity.this, CalendarActivity.class);
                        startActivity(intent2);
                        return true;
                    case R.id.packages:
                        Intent intent3 = new Intent(NewsFeedActivity.this, PackageActivity.class);
                        startActivity(intent3);
                        return true;
                }
                return false;
            }
        });
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
                Intent create_packages = new Intent(NewsFeedActivity.this, Create_packages.class);
                startActivity(create_packages);
                return true;
            case R.id.o_create_event:
                Intent create_event = new Intent(NewsFeedActivity.this,Create_Event.class);
                startActivity(create_event);
                return true;
            case R.id.o_settings:
                Toast.makeText(this, "Go To Settings", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.o_logout:
                Intent intent = new Intent(NewsFeedActivity.this, Login_Activity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void update_events() {

        onSchedule_Events.addListenerForSingleValueEvent(new ValueEventListener() {
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
                    Calendar calendar_start = NewsFeedActivity.this.get_calendar(start_date);
                    Calendar calendar_end = get_calendar(end_date_str);
                    Calendar startTime = Calendar.getInstance();
                    Date date = calendar_start.getInstance().getTime();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
                    String strDate = dateFormat.format(date);
                    Log.i("The calendar event date",strDate);
                    WeekViewEvent single_event = new WeekViewEvent(1, entry.getKey() , calendar_start,calendar_end);
                    Log.i("The event details",single_event.toString());



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
}
