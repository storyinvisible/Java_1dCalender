package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mRef = mDatabase.getRef();

        mDatabase.child("Community Users").setValue("ruihan");
        mDatabase.child("Community Users").child("Roles").setValue("what is rules");
        mDatabase.child("Community Users").child("User ID").setValue(1003784);

        mDatabase.child("Community Users").child("Micro community").setValue(0);
        mDatabase.child("Community Users").child("Micro community").child("istd cohort1").setValue("istd cohort1");
        mDatabase.child("Community Users").child("Micro community").child("ballroom dance").setValue("ballroom dance");

        mDatabase.child("Community Users").child("Micro community").child("istd cohort1").child("admins").setValue(1000000);
        ArrayList<Integer> normal_users = new ArrayList<>(5);
        normal_users.add(1003785);
        normal_users.add(1003765);
        normal_users.add(1003755);
        normal_users.add(1003745);
        normal_users.add(1003715);
        mDatabase.child("Community Users").child("Micro community").child("istd cohort1").child("normal users").setValue(normal_users);

        mDatabase.child("Community Users").child("Packge").setValue(0);
        mDatabase.child("Community Users").child("Packge").child("machine learning").setValue("machine learning");
        mDatabase.child("Community Users").child("Packge").child("machine learning").setValue("machine learning");

        mDatabase.child("Community Users").child("Packge").child("B").child("details").setValue("a stupid class");
        mDatabase.child("Community Users").child("Packge").child("B").child("latest update").setValue("project part1 due");






        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                String value = dataSnapshot.child("Community Users").child("Packge").child("B").child("latest update").getValue(String.class);
                System.out.println(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }







}
