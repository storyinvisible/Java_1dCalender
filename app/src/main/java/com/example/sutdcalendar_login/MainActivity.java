package com.example.sutdcalendar_login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button signin;
    private TextView signup;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    /**Continue with ruihan's firebase_database*/
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=findViewById(R.id.etusername);
        password=findViewById(R.id.etpassword);
        signin=findViewById(R.id.btnsignin);
        signup=findViewById(R.id.tvsignup);
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        FirebaseUser user=firebaseAuth.getCurrentUser();
        /*
        mDatabase.child("Community Users").setValue("ruihan");
        mDatabase.child("Community Users").child("Roles").setValue("What is role");
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
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                String value = dataSnapshot.child("Community Users").child("Packge").child("B").child("latest update").getValue(String.class);
                System.out.println(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
         */
        if (user!=null) {
            finish();
            startActivity(new Intent(MainActivity.this,LoggedInActivity.class));
        }
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_email=username.getText().toString();
                String str_password=password.getText().toString();
                progressDialog.setMessage("Processing Logging in...");
                progressDialog.show();
                firebaseAuth.signInWithEmailAndPassword(str_email,str_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this,"Logged in successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this,LoggedInActivity.class));
                        } else
                            Toast.makeText(MainActivity.this,"Logged in failed",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegistrationActivity.class));

            }
        });
    }
}