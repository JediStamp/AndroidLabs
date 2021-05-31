package com.example.androidlabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class FirstActivity extends AppCompatActivity {
    SharedPreferences prefs;
    EditText editTextEmail;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);

        //Initialize email text
        editTextEmail = findViewById(R.id.editTextEmail);

        //Load the sharedPreferences
        prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        String savedEmail = prefs.getString("Email", "");
        editTextEmail.setText(savedEmail);

        //Define Next Activity
        Intent nextPage = new Intent(this, ProfileActivity.class);

        //Set on click listener for login button to move to second activity
        loginButton = findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(
                click  -> startActivity( nextPage)
        );
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Save user's email address
        saveSharedPrefs(editTextEmail.getText().toString());
    }

    private void saveSharedPrefs(String stringToSave)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Email", stringToSave);
        editor.commit();
    }


}
