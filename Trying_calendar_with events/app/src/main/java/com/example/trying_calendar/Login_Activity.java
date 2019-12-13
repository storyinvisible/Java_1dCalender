package com.example.trying_calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseUser;
/**Here we implemented a simple login activity
 * utilizing firebase authentication
 * if the user don't have the account,
 * we could also let them register*/
public class Login_Activity extends AppCompatActivity {
    /**instantiate widget*/
    private EditText username;
    private EditText password;
    private Button signin;
    private TextView signup;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        /**Find view by id*/
        username=findViewById(R.id.etusername);
        password=findViewById(R.id.etpassword);
        signin=findViewById(R.id.btnsignin);
        signup=findViewById(R.id.tvsignup);
        firebaseAuth= FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        /**Here if user is already logged in,
         * next time we don't require them to log in again*/
        FirebaseUser user=firebaseAuth.getCurrentUser();

        if (user!=null) {
            finish();
            startActivity(new Intent(Login_Activity.this,CalendarActivity.class));
        }
        /**Click signin button, verify by firebaseAuth*/
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
                            Toast.makeText(Login_Activity.this,"Logged in successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login_Activity.this,CalendarActivity.class));
                        } else
                            Toast.makeText(Login_Activity.this,"Logged in failed",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        /**Direct the user to register activity*/
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Activity.this,RegistrationActivity.class));

            }
        });
    }
}
