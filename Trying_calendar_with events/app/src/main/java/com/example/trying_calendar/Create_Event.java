package com.example.trying_calendar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class Create_Event extends AppCompatActivity {

    Button btn_back_to_basic;
    EditText eventEdit;
    TextView date_from;
    TextView date_to;
    TextView fromTimeEdit;
    TextView toTimeEdit;
    TextView invitefriends;
    EditText detailsEdit;
    Button btn_ok;
    DatePickerDialog.OnDateSetListener mDateSetListener1;
    DatePickerDialog.OnDateSetListener mDateSetListener2;
    TimePickerDialog timePickerDialog1;
    TimePickerDialog timePickerDialog2;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    String amPm;
    /**Firebase instance*/
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    /**Recycler View*/
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    String event, details;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        btn_back_to_basic = findViewById(R.id.btn_back_to_basic);
        eventEdit = findViewById(R.id.eventEdit);
        date_from = findViewById(R.id.date_from);
        date_to = findViewById(R.id.date_to);
        fromTimeEdit = findViewById(R.id.fromTimeEdit);
        toTimeEdit = findViewById(R.id.toTimeEdit);
        detailsEdit = findViewById(R.id.detailsEdit);
        btn_ok = findViewById(R.id.btn_ok);
        invitefriends=findViewById(R.id.tvInvite);
        DatabaseReference mdatabaseRef=database.getReference("User");
        /**Recycler View starts here*/
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ArrayList<Listed_user> userToInvite=new ArrayList<>();
        /*
        for (int i=0;i<10;i++) {
            Listed_user listed_user=new Listed_user("Joson"+(i+1),"Student");
            userToInvite.add(listed_user);

        }
        //Log.i("Shaozuo",String.valueOf(userToInvite.size()));
         */


        //TODO: DatabaseReference get user name
        mdatabaseRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if (dataSnapshot.exists()) {
                   for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                       Log.i("Shaozuo",snapshot.child("Name Node").getValue().toString());
                       try {
                           Listed_user listed_user=new Listed_user(
                                   snapshot.child("Name Node").getValue().toString(),snapshot.child("role").getValue().toString());
                           userToInvite.add(listed_user);
                           adapter=new UserAdaptor(userToInvite,Create_Event.this);
                           recyclerView.setAdapter(adapter);
                       } catch (NullPointerException ex) {
                           Log.i("Shaozuo",ex.getMessage());
                       }

                   }
               }

           }
           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {
           }
        });

        //ToDo: BACK BUTTON task
        btn_back_to_basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("create events", "im trying to go back to the weekly calendar");
                Intent intent = new Intent(Create_Event.this, Welcome_Page.class);
                startActivity(intent);
            }
        });


        //ToDo: OK BUTTON task
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event = eventEdit.getText().toString();
                details = detailsEdit.getText().toString();

                showToast(event);
                showToast(details);

                //ToDo: make the events and details appear on basic activity
            }
        });

        //ToDo: CHOOSE FROM DATE
        date_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Create_Event.this,
                        R.style.Theme_AppCompat_DayNight, mDateSetListener1, year,
                        month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

                dialog.show();
            }
        });

        //initialise on date set listener object
        mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d("TAG", "onDateSet: dd/mm/yyyy: " + dayOfMonth + "/" + month + "/" + year);
                month = month + 1; //jan= 0+1
                String date = dayOfMonth + "/" + month + "/" + year;
                date_from.setText(date);
            }
        };

        //ToDo: CHOOSE TO DATE
        date_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Create_Event.this,
                        R.style.Theme_AppCompat_DayNight, mDateSetListener2, year,
                        month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

                dialog.show();
            }
        });

        //initialise on date set listener object
        mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d("TAG", "onDateSet: dd/mm/yyyy: " + dayOfMonth + "/" + month + "/" + year);
                month = month + 1; //jan= 0+1
                String date = dayOfMonth + "/" + month + "/" + year;
                date_to.setText(date);
            }
        };

        //ToDo: CHOOSE FROM TIME
        fromTimeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog1 = new TimePickerDialog(Create_Event.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        fromTimeEdit.setText(hourOfDay + ":" + minute);
                    }
                }, currentHour, currentMinute, false);
                timePickerDialog1.show();
            }
        });

        //ToDo: CHOOSE TO TIME
        toTimeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog2 = new TimePickerDialog(Create_Event.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        toTimeEdit.setText(hourOfDay + ":" + minute);
                    }
                }, currentHour, currentMinute, false);
                timePickerDialog2.show();
            }
        });

    }

    private void showToast(String text) {
        Toast.makeText(Create_Event.this, text, Toast.LENGTH_SHORT).show();
    }


}
