package com.example.trying_calendar;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsFeed_adaptor extends RecyclerView.Adapter<NewsFeed_adaptor.ViewHolder> {
    private ArrayList<Events_details> EventList;
    private Context context;
    NewsFeed_adaptor(ArrayList<Events_details> EventList,Context context){
        this.EventList= EventList;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View items= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_event,parent,false);
        return new ViewHolder(items);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.event_name.setText(EventList.get(position).name);
        Events_details single_event_detail=EventList.get(position);
        String date_info= single_event_detail.date_from+ "From :" + single_event_detail.time_from + " to " + single_event_detail.time_to;
        holder.event_dates.setText(date_info);
        holder.event_descr.setText(single_event_detail.Description);
    }

    @Override
    public int getItemCount() {
        return EventList.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView event_name;
        TextView event_dates;
        TextView event_descr;
        LinearLayout layout;
        public ViewHolder(View items){
            super(items);
            event_name=items.findViewById(R.id.cardvieweventname);
            event_dates=items.findViewById(R.id.startnendtime);
            event_descr=items.findViewById(R.id.eventDescription);
            layout= items.findViewById(R.id.eventcards);
        }
    }
}
