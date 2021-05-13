package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonName;
        buttonName = (Button) findViewById(R.id.button);
        buttonName.setOnClickListener((v) ->  {
            finish();
            System.exit(0);
        });
    }
}