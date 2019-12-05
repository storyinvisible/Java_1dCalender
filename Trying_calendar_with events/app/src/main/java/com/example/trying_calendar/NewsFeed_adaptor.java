package com.example.trying_calendar;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class NewsFeed_adaptor extends RecyclerView.Adapter<NewsFeed_adaptor.ViewHolder> {
    private ArrayList<Events_details> EventList;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRSVP;
    DatabaseReference mUserevent;
    private Context context;
    NewsFeed_adaptor(ArrayList<Events_details> EventList,Context context){
        this.EventList= EventList;
        this.context=context;
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user_firebase = firebaseAuth.getCurrentUser();
        String email = user_firebase.getEmail().toString();
        String user = new RegistrationActivity().emailToName(email);
        mRSVP= database.getReference().child("User").child(user).child("RSVP events");
        mUserevent= database.getReference().child("User").child(user).child("event");
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View items= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_event,parent,false);
        return new ViewHolder(items);
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.event_name.setText(EventList.get(position).name);
        final Events_details single_event_detail=EventList.get(position);
        String date_info= single_event_detail.date_from + " | From " + single_event_detail.time_from + " | To " + single_event_detail.time_to;
        holder.event_dates.setText(date_info);
        holder.event_descr.setText(single_event_detail.Description);
        if((single_event_detail.RSVP)){
            holder.RSVP_button.setText("RSVP");
        }
        else {
            holder.RSVP_button.setText("Remove Event");
        }
        holder.RSVP_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.RSVP_button.getText().toString()=="RSVP"){
                    replyRSVP(single_event_detail);
                    single_event_detail.RSVP=false;
                    holder.RSVP_button.setText("Remove");
                }
                else if(holder.RSVP_button.getText().toString()=="RSVP"){
                    EventList.remove(single_event_detail);
                    NewsFeed_adaptor.this.notifyDataSetChanged();
                }
            }
        });

    }
    private void replyRSVP(Events_details details){

        mRSVP.child(details.getName()).setValue(null);
        mUserevent.child(details.getName()).setValue(details.getHashmao());

    }
    private void remove_event(Events_details details){
        mUserevent.child(details.getName()).setValue(null);
        EventList.remove(details);
        this.notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return EventList.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView event_name;
        TextView event_dates;
        TextView event_descr;
        Button RSVP_button;
        LinearLayout layout;
        public ViewHolder(View items){
            super(items);
            event_name=items.findViewById(R.id.cardvieweventname);
            event_dates=items.findViewById(R.id.startnendtime);
            event_descr=items.findViewById(R.id.eventDescription);
            RSVP_button= items.findViewById(R.id.RSVP);
            layout= items.findViewById(R.id.eventcards);
        }
    }
}
