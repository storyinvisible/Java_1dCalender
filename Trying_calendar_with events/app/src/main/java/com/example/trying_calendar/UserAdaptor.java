package com.example.trying_calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdaptor extends RecyclerView.Adapter<UserAdaptor.ViewHolder> {

    private ArrayList<Listed_user> userList;
    private Context context;

    public UserAdaptor(ArrayList<Listed_user> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.listed_user,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewUser.setText(userList.get(position).getUsername());
        holder.textViewDesc.setText(userList.get(position).getDesc());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox textViewUser;
        public TextView textViewDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUser=itemView.findViewById(R.id.tvUsername);
            textViewDesc=itemView.findViewById(R.id.tvDesc);
        }
    }
}
