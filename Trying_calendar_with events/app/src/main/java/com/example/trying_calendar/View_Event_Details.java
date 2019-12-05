package com.example.trying_calendar;

import android.content.Intent;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class View_Event_Details extends AppCompatActivity {

    Button btn_back_to_basic;
    TextView eventTitleTextView;
    TextView ddmmyyyyTextView;
    TextView detailsTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_event);

        eventTitleTextView = findViewById(R.id.eventTitleTextView);
        ddmmyyyyTextView = findViewById(R.id.ddmmyyyyTextView);
        detailsTextView = findViewById(R.id.detailsTextView);
        btn_back_to_basic = findViewById(R.id.btn_back_to_basic);

        btn_back_to_basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("view events", "im trying to view the event page");
                Intent intent = new Intent(View_Event_Details.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
    }
}
