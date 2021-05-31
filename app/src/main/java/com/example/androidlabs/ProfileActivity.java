package com.example.androidlabs;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    SharedPreferences prefs;
    ImageButton imgButton;
    EditText editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imgButton = findViewById(R.id.imgButton2Photo);
        imgButton.setOnClickListener(v -> dispatchTakePictureIntent());

        editTextEmail = findViewById(R.id.editT2Email);
        prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        String savedEmail = prefs.getString("Email", "");
        editTextEmail.setText(savedEmail);

        Log.e(ACTIVITY_NAME,"In function: OnCreate()" );

    }

    private void dispatchTakePictureIntent() {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }

    @Override
    protected void onStart() {
        super.onStart();

        Log.e(ACTIVITY_NAME,"In function: OnStart()" );
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.e(ACTIVITY_NAME,"In function: OnResume()" );
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.e(ACTIVITY_NAME,"In function: OnPause()" );
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.e(ACTIVITY_NAME,"In function: OnStop()" );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.e(ACTIVITY_NAME,"In function: OnDestroy()" );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgButton.setImageBitmap(imageBitmap);
        }

        Log.e(ACTIVITY_NAME,"In function: OnActivityResult()" );
    }

}