package com.example.trying_calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class Create_packages extends AppCompatActivity {
    Calendar myCalender ;
    DatePickerDialog.OnDateSetListener startdate_listener;
    DatePickerDialog.OnDateSetListener enddate_listener;
    TimePickerDialog start_time_picker;
    TimePickerDialog end_time_picker;
    TextView startdate;
    TextView enddate;
    TextView start_time;
    TextView end_time;
    int currentHour;
    int currentMinute;
    String amPm;
    Spinner packages_for;FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_packages);
        DatabaseReference myRef = database.getReference("Community");
        packages_for= findViewById(R.id.packages_community);
        final List<String> commnity_list = new ArrayList<>();
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, commnity_list);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        packages_for.setAdapter(spinnerAdapter);
        myRef.child("Micro community").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                for(DataSnapshot choices:dataSnapshot.getChildren()){
                    String choice = choices.getKey();
                    commnity_list.add(choice);
                    spinnerAdapter.notifyDataSetChanged();
                    Log.i("The children",choice);

                }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        startdate =findViewById(R.id.start_date);
        enddate= findViewById(R.id.end_date);
        start_time = findViewById(R.id.start_time);
        end_time= findViewById(R.id.end_time);
        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Create_packages.this,
                        R.style.Theme_AppCompat_DayNight,startdate_listener, year,
                        month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

                dialog.show();
            }
        });
        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Create_packages.this,
                        R.style.Theme_AppCompat_DayNight,enddate_listener, year,
                        month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

                dialog.show();
            }
        });
        startdate_listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d("TAG", "onDateSet: dd/mm/yyyy: " + dayOfMonth + "/" + month + "/" + year);
                month = month + 1; //jan= 0+1
                String date = dayOfMonth + "/" + month + "/" + year;
                startdate.setText(date);
            }
        };
        enddate_listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d("TAG", "onDateSet: dd/mm/yyyy: " + dayOfMonth + "/" + month + "/" + year);
                month = month + 1; //jan= 0+1
                String date = dayOfMonth + "/" + month + "/" + year;
                enddate.setText(date);
            }
        };
        start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalender = Calendar.getInstance();
                currentHour = myCalender.get(Calendar.HOUR_OF_DAY);
                currentMinute = myCalender.get(Calendar.MINUTE);
                start_time_picker = new TimePickerDialog(Create_packages.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        start_time.setText(hourOfDay + ":" + minute);
                    }}, currentHour, currentMinute, false);
                start_time_picker.show();

            }
        });
        end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalender = Calendar.getInstance();
                currentHour = myCalender.get(Calendar.HOUR_OF_DAY);
                currentMinute = myCalender.get(Calendar.MINUTE);
                end_time_picker = new TimePickerDialog(Create_packages.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        end_time.setText(hourOfDay + ":" + minute);
                    }}, currentHour, currentMinute, false);
                end_time_picker.show();

            }
        });





    }


    /*
    private void updateStartDate(){
        String format = "dd/MM/yy";
        SimpleDateFormat dateFormat= new SimpleDateFormat(format);

        startdate.setText(dateFormat.format(myCalender.getTime()));

    }

     */

}
