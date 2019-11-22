package com.example.sutdcalendar_login;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

class node{
    String name = "0";
    String value = "0";
}

public class firebaseMethod {

    private String[] values = new String[3];
    private HashMap<String,String> map = new HashMap<>();
    public void set_child(DatabaseReference ref, String node_name, String node_value){
        ref.child(node_name).setValue(node_value);
    }
    public void setValue(DatabaseReference ref, String value){
        ref.setValue(value);
    }
    public String get_node(DatabaseReference ref){

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valuesa = dataSnapshot.getValue(String.class);
                values[0] = valuesa;

                System.out.println(values[0]);
                valuesa=valuesa+"onkjn";
                System.out.println(valuesa);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }

        });
        return values[0];
    }

//    public String getValues() {
//        String out="";
//
//        for(String keys: map.keySet()){
//
//            out=keys;
//        }
//        return out;
//    }
}
