package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
//import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
//import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_relative);
//        setContentView(R.layout.activity_main_linear);
//        setContentView(R.layout.activity_main_grid);
//        setContentView(R.layout.activity_lab3);

//        // Text Boxes
//        TextView viewText;
//        viewText = findViewById(R.id.textView);
//
//        EditText editName;
//        editName = findViewById(R.id.editText);
//
//        // Buttons
//        Button buttonName;
//        buttonName = findViewById(R.id.button);
//        buttonName.setOnClickListener(
//                (v) -> Toast.makeText(
//                        MainActivity.this,
//                        getResources().getString(R.string.toast_message),
//                        Toast.LENGTH_LONG).show()
//        );
//
//        ImageButton buttonImg;
//        buttonImg = findViewById(R.id.imageButton);
//
//        // Checkbox and Switch
//        CheckBox checkName;
//        checkName = findViewById(R.id.checkBox);
//        checkName.setOnCheckedChangeListener(( cb,  b) -> {
//            String msg = getResources().getString(R.string.snack_switch);
//            if (b) {
//                msg += getResources().getString(R.string.on);
//            }else {
//                msg += getResources().getString(R.string.off);
//            }
//
//            Snackbar.make(viewText, msg, Snackbar.LENGTH_LONG)
//                    .setAction(getResources().getString(R.string.undo), click-> cb.setChecked( !b ))
//                    .show();
//        });
//
//        Switch mySwitch;
//        mySwitch = findViewById(R.id.switch1);
//        mySwitch.setOnCheckedChangeListener(( sw,  b) -> {
//            String msg = getResources().getString(R.string.snack_check);
//            if (b) {
//                msg += getResources().getString(R.string.on);
//            }else {
//                msg += getResources().getString(R.string.off);
//            }
//            Snackbar.make(editName, msg, Snackbar.LENGTH_LONG)
//                    .setAction(getResources().getString(R.string.undo), click-> sw.setChecked( !b ))
//                    .show();
//        });
    }
}