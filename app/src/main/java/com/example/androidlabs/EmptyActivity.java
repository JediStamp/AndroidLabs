package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EmptyActivity extends AppCompatActivity {
//    public static final String ITEM_SELECTED = "ITEM";
//    public static final String ITEM_POSITION = "POSITION";
//    public static final String ITEM_ID = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        Bundle dataToPass = getIntent().getExtras(); //get the data that was passed from FragmentExample


        // Fragment transaction that calls replace
        DetailsFragment dFragment = new DetailsFragment(); //add a DetailFragment
        dFragment.setArguments( dataToPass ); //pass it a bundle for information
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_location, dFragment) //Add the fragment in FrameLayout
                .commit(); //actually load the fragment.
    }
}