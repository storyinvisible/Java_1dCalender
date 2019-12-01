package com.example.trying_calendar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NewsFeedActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);

        TextView text = findViewById(R.id.textView5);
        text.setText("This is the News Feed!");

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
}
