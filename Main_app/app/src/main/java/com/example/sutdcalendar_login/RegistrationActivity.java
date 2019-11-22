package com.example.sutdcalendar_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class RegistrationActivity extends AppCompatActivity {
    private EditText name;
    private EditText email;
    private EditText password;
    private EditText studentid;
    private Button signup;
    private TextView signupback;
    private SignupInfo signupInfo;
    private FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mRef = mDatabase.getRef();
    DatabaseReference userinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        name=findViewById(R.id.etsignupname);
        email=findViewById(R.id.etsignupemail);
        password=findViewById(R.id.etsignupcode);
        studentid=findViewById(R.id.etsignupidnum);
        signup=findViewById(R.id.btnsignupcomplete);
        signupback=findViewById(R.id.tvsignupback);
        //Log.i("Shaozuo",password.getText().toString());

        firebaseAuth=FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_name= name.getText().toString();
                String str_email=email.getText().toString();
                String str_password=password.getText().toString();
                String str_studentid=studentid.getText().toString();
                //signupInfo=new SignupInfo(str_name,str_email,str_password);
                firebaseAuth.createUserWithEmailAndPassword(str_email,str_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this,"Sign up successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                            int user = Integer.valueOf(name.getText().toString());
                             mRef.child("User").setValue(user);
                        } else Toast.makeText(RegistrationActivity.this,"Sign up failed",Toast.LENGTH_SHORT).show();
            }
        });
            }
        });
        signupback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
            }
        });

    }

    public void updateToFirebase(String name, String studentid) {
        DatabaseReference mDataBase=FirebaseDatabase.getInstance().getReference();
        mDataBase.child("Community Users").setValue(name);
        mDataBase.child("Community Users").child("User ID").setValue(Integer.parseInt(studentid));
    }
}
