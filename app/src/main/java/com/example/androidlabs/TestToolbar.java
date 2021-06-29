package com.example.androidlabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
//import android.widget.Toolbar;

public class TestToolbar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        // Initialize ToolBar
        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar); //this is the one it says to use in module

        //Initialize Drawer
        DrawerLayout myDrawer = (DrawerLayout)findViewById(R.id.drawer);
        ActionBarDrawerToggle myDToggle;
        myDToggle = new ActionBarDrawerToggle(this, myDrawer, myToolbar, R.string.open, R.string.close);

        myDrawer.addDrawerListener(myDToggle);
        myDToggle.syncState();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        NavigationView navView = (NavigationView)findViewById(R.id.navView);
        navView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sample_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String msg = "";
        switch (item.getItemId()){
            case R.id.itemRebel:
                msg = getString(R.string.msgRebel);
                break;
            case R.id.itemBatman:
                msg = getString(R.string.msgBatman);
                break;
            case R.id.itemFlash:
                msg = getString(R.string.msgFlash);
                break;
            case R.id.item4:
                msg = getString(R.string.msgOverflow);
                break;
        }
//    if (!msg.equals("")) {
        Toast.makeText(TestToolbar.this, msg, Toast.LENGTH_LONG).show();
//    }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.itemChat:
                Intent nextPage = new Intent(this, ChatRoomActivity.class);
                startActivity(nextPage);
                break;
            case R.id.itemWeather:
                Intent wxPage = new Intent(this, WeatherForcastActivity.class);
                startActivity(wxPage);
                break;
            case R.id.itemLogin:
                setResult(500);
                finish();
                break;
        }
        DrawerLayout myDrawer = (DrawerLayout)findViewById(R.id.drawer);
        myDrawer.closeDrawer(GravityCompat.START);
        return false;
    }
}