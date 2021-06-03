package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatRoomActivity extends AppCompatActivity {
    private ArrayList<String> elements = new ArrayList<>();
    private MyListAdapter myAdapter;
    private EditText myChat;
    private Message curMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        // Create a List View
        ListView myList = findViewById(R.id.theListView);
        myList.setAdapter(myAdapter = new MyListAdapter());
        myList.setOnItemLongClickListener( (parent, view, position, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Do you want to delete this?")

                    //What is the message:
                    .setMessage("The selected row is: " + position + "\n"
                    + "The selected database is: " + id)

                    //what the Yes button does:
                    .setPositiveButton("Yes", (click, arg) -> {
                        elements.remove(position);
                        myAdapter.notifyDataSetChanged();
                    })
                    //What the No button does:
                    .setNegativeButton("No", (click, arg) -> { })

                    //An optional third button:
//                    .setNeutralButton("Maybe", (click, arg) -> {  })

                    //You can add extra layout elements:
//                    .setView(getLayoutInflater().inflate(R.layout.row_layout, null) )

                    //Show the dialog
                    .create().show();


            return true;
        } );

        // Add chat text
        EditText myChat = findViewById(R.id.chatText);

        // Add Send Button
        Button sendTextButton = findViewById(R.id.sendButton);
        sendTextButton.setOnClickListener(click -> {
            curMsg = new Message(myChat.getText().toString(), "Send");
            elements.add(myChat.getText().toString());
            myAdapter.notifyDataSetChanged();
            myChat.setText("");
        });

        //Add Rx Button
        Button rxTextButton = findViewById(R.id.rxButton);
        rxTextButton.setOnClickListener(click -> {
            curMsg = new Message(myChat.getText().toString(), "Rx");
            elements.add(myChat.getText().toString());
            myAdapter.notifyDataSetChanged();
            myChat.setText("");
        });

    }

private class MyListAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return elements.size();
    }

    @Override
    public Object getItem(int position) {
        return elements.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    @Override
    public View getView(int position, View old, ViewGroup parent) {
        View newView = old;
        LayoutInflater inflater = getLayoutInflater();

        // make a new row
        if (newView == null){
            newView = inflater.inflate(curMsg.getLayout(), parent, false);
        }

        //set text for new row
        TextView tView = newView.findViewById(R.id.textGoesHere);
        tView.setText(getItem(position).toString());

        // return new row to be added to table
        return newView;
    }
}

private class Message {
        String msgText;
        String msgType;
    public Message (String msgText, String msgType){
        this.msgText = msgText;
        this.msgType = msgType;
    }

    public int getLayout(){
        int layout;
        if (msgType == "Send"){
            layout = R.layout.send_row_layout;
        }
        else if (msgType == "Rx"){
            layout = R.layout.rx_row_layout;
        }
        else {
            layout = 0;
        }
        return layout;
    }


}

}