package com.example.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class DetailsFragment extends Fragment {

    private AppCompatActivity parentActivity;
    private long id;
    private Bundle dataFromActivity;

    public static DetailsFragment newInstance(String param1, String param2) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataFromActivity = getArguments();
        id = dataFromActivity.getLong(ChatRoomActivity.MSG_ID );

        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_details, container, false);
        TextView tv1 = (TextView)result.findViewById(R.id.frag_tv1);
        TextView tv2 = (TextView)result.findViewById(R.id.frag_tv2);
        CheckBox cb1 = (CheckBox)result.findViewById(R.id.frag_cb2);

        tv1.setText(dataFromActivity.getString(ChatRoomActivity.MSG_TEXT));
        tv2.setText("ID: " + id);

        if (dataFromActivity.getString(ChatRoomActivity.MSG_TYPE).equals("Send")){
            cb1.setChecked(true);
        } else{
            cb1.setChecked(false);
        }

        // get the delete button, and add a click listener:
        Button finishButton = (Button)result.findViewById(R.id.frag_button);
        finishButton.setOnClickListener( clk -> {
            if (ChatRoomActivity.istablet){
                //Tell the parent activity to remove - tablet
                parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
            } else {
                    //Remove phone
                Intent backToFragmentExample = new Intent();
                backToFragmentExample.putExtra(ChatRoomActivity.MSG_ID, dataFromActivity.getLong(ChatRoomActivity.MSG_ID ));

                parentActivity.setResult(Activity.RESULT_OK, backToFragmentExample); //send data back to FragmentExample in onActivityResult()
                parentActivity.finish(); //go back
            }
            });
            return result;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        parentActivity = (AppCompatActivity)context;
    }
}