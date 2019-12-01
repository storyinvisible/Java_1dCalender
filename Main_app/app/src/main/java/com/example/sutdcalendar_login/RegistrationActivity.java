package com.example.sutdcalendar_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private Button signup;
    private TextView signupback;
    private SignupInfo signupInfo;
    private FirebaseAuth firebaseAuth;
    //TODO: Add Widget here
    private EditText studentid;
    private RadioGroup roleGroup;
    private RadioGroup pillarGroup;
    private RadioButton role;
    private RadioButton pillar;
    /**Add widgets done*/
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
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
        roleGroup=findViewById(R.id.rgrolesel);
        pillarGroup=findViewById(R.id.rgpillarsel);
        //Log.i("Shaozuo",password.getText().toString());

        firebaseAuth=FirebaseAuth.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String str_name= name.getText().toString();
                String str_email=email.getText().toString();
                String str_password=password.getText().toString();
                final String str_studentid=studentid.getText().toString();
                int roleid=roleGroup.getCheckedRadioButtonId();
                final int pillarid=pillarGroup.getCheckedRadioButtonId();
                if (checkInfo(str_name,str_email,str_password,str_studentid,roleid,pillarid)) {
                    try {
                        firebaseAuth.createUserWithEmailAndPassword(str_email, str_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegistrationActivity.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                                    role=findViewById(roleGroup.getCheckedRadioButtonId());
                                    pillar=findViewById(pillarGroup.getCheckedRadioButtonId());
                                    Log.i("Shaozuo",role.getText().toString());
                                    Log.i("Shaozuo",pillar.getText().toString());
                                    mDatabase.child("User").child(str_studentid).child("Community").child(pillar.getText().toString()).setValue(role.getText().toString());
                                } else
                                    Toast.makeText(RegistrationActivity.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (IllegalArgumentException ex) {
                        Toast.makeText(RegistrationActivity.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
                    }}
                else {
                    Toast.makeText(RegistrationActivity.this,"Please fill in all the info as required",Toast.LENGTH_SHORT).show();
                }

            }
        });
        signupback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
            }
        });

    }
    public boolean checkInfo(String name, String email, String password, String studentid, int roleid, int pillarid) {
        if (name.isEmpty()) {
            return false;
        } else if (email.isEmpty()) {
            return false;
        } else if (password.isEmpty()) {
            return false;
        } else if (studentid.isEmpty()) {
            return false;
        } else if (roleid==-1|pillarid==-1) {
            return false;
        } return true;
    }
}