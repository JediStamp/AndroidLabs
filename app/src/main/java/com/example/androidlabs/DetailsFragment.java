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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
    private AppCompatActivity parentActivity;
    private long id;
    private Bundle dataFromActivity;

//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(String param1, String param2) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//

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