package com.example.trying_calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class MultipleDialogFragment extends DialogFragment {
    private ArrayList<String> userArrayList=new ArrayList<>();
    public interface onMultiChoiceListener{
        void onPositiveButtonClicked(String[] list, ArrayList<String> selectedItemList);
        void onNegativeButtonClicked();
    }
    onMultiChoiceListener mListener;

    MultipleDialogFragment(ArrayList<String> userArrayList) {
        this.userArrayList=userArrayList;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener=(onMultiChoiceListener) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString()+"onMultiChoiceListener must be implemented");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final ArrayList<String> selectedUser=new ArrayList<>();
        final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        final String[] userArray=ArrayListToArray(this.userArrayList);
        builder.setTitle("Invite your friends: ")
                .setMultiChoiceItems(userArray, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            selectedUser.add(userArray[which]);
                        } else {
                            selectedUser.remove(userArray[which]);
                        }
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onPositiveButtonClicked(userArray,selectedUser);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onNegativeButtonClicked();
                    }
                });

        return builder.create();

    }
    public String[] ArrayListToArray(ArrayList<String> userArrayList) {
        String[] userlist=new String[userArrayList.size()];
        for (int i=0;i<userArrayList.size();i++) {
            userlist[i]=userArrayList.get(i);
        }
        return userlist;
    }
}
