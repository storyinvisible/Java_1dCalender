package com.example.trying_calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Welcome_Page extends AppCompatActivity {
    Button goToCalendar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        goToCalendar = findViewById(R.id.btn_goToCalendar);
        goToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome_Page.this, Login_Activity.class);
                startActivity(intent);
            }
        });


    }
}