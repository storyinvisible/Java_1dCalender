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

/**This is the multiple dialog fragment(not an activity)
 * that belongs to Create_Event Activity*/
public class MultipleDialogFragment extends DialogFragment {
    private ArrayList<String> userArrayList;

    /**For Create_Event activity to implement*/
    public interface onMultiChoiceListener{
        void onPositiveButtonClicked(String[] list, ArrayList<String> selectedItemList);
        void onNegativeButtonClicked();
    }
    onMultiChoiceListener mListener;
    /**userArrayList listed all the users that could be potentially invited*/
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
        /**These can the standard steps to build the dialog*/
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

    /**Helper function: takes in ArrayList<String>, return its String[]*/
    public String[] ArrayListToArray(ArrayList<String> userArrayList) {
        String[] userlist=new String[userArrayList.size()];
        for (int i=0;i<userArrayList.size();i++) {
            userlist[i]=userArrayList.get(i);
        }
        return userlist;
    }
}
