package com.example.trying_calendar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class PackageActivity extends AppCompatActivity {
    final String PACKAGES = "Packages";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userPackages;
    DatabaseReference myRef; // To get the event list from the community
    String current_user;
    LinearLayout Packages_layout;
    int check_box_id = 1000;
    int check_box_count=0;
    Button add_packages;
    private FirebaseAuth firebaseAuth;

    HashMap<String,String> package_to_import= new HashMap<String, String>();
    final HashMap<String, Object> Unique_event= new HashMap<String, Object>() ;// the event the current suer have
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages);

        firebaseAuth = FirebaseAuth.getInstance();
        Packages_layout= findViewById(R.id.packages_list);
        add_packages=findViewById(R.id.add_packages);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        myRef = database.getReference("Community");
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();// get current user

        userPackages= database.getReference().child("User").child(PACKAGES);
        if (user != null) {
            // Name, email address, and profile photo Url

            String email = user.getEmail();

            current_user= new RegistrationActivity().emailToName(email);
        }
        userPackages= database.getReference().child("User").child(current_user).child(PACKAGES);
        get_current_packages();
        //highlight menu items when clicked
        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        add_packages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPackages.setValue(package_to_import);
                Intent intent = new Intent(PackageActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.newsfeed:
                        Intent intent1 = new Intent(PackageActivity.this, NewsFeedActivity.class);
                        startActivity(intent1);
                        return true;
                    case R.id.calendar:
                        Intent intent2 = new Intent(PackageActivity.this, CalendarActivity.class);
                        startActivity(intent2);
                        return true;
                }
                return false;
            }
        });
    }
    public void get_list_of_packages(){ // get the list of packages of the users
        myRef.child(PACKAGES).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> package_list= new ArrayList<>();
                for(DataSnapshot choices:dataSnapshot.getChildren()) {
                    String package_names = choices.getKey();
                    if(Unique_event.get(package_names)==null){ // if it is not a packages that the user already have
                        package_list.add(package_names);
                    }


                    Log.i("Packages Imported", package_names);
                }
                PackageActivity.this.add_checkbox(package_list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void get_current_packages(){ // get all the other packages that the users
        userPackages.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot choices:dataSnapshot.getChildren()) {
                    String package_names = choices.getKey();
                    Unique_event.put(package_names, choices.getValue());
                }
                get_list_of_packages();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void add_checkbox(ArrayList<String> package_list){// add the check box

        for(String packages: package_list){
            final CheckBox box= new CheckBox(this);
            box.setId(check_box_id);
            check_box_id++;
            box.setText(packages);
            box.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        package_to_import.put(box.getText().toString(),box.getText().toString());

                    }
                    else{
                        package_to_import.remove(box.getText().toString());
                    }
                }
            });
            Packages_layout.addView(box);
        }
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
                Intent create_packages = new Intent(PackageActivity.this, Create_packages.class);
                startActivity(create_packages);
            case R.id.o_create_event:
                Intent create_event = new Intent(PackageActivity.this,Create_Event.class);
                startActivity(create_event);
                return true;
            case R.id.o_settings:
                Toast.makeText(this, "Go To Settings", Toast.LENGTH_SHORT).show();
                return true;
                //TODO: LOGOUT DOES NOT WORK PROPERLY< CRASHES THE APP
            case R.id.o_logout:
                firebaseAuth.signOut();
                finish();
                Intent logouti = new Intent(PackageActivity.this, Login_Activity.class);
                Toast.makeText(this, "logout was runned", Toast.LENGTH_SHORT).show();
                startActivity(logouti);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
