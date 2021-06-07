package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    private ArrayList<Message> elements = new ArrayList<>(); // Messages
    private MyListAdapter myAdapter;
    private Message curMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        // Create a List View
        ListView myList = findViewById(R.id.theListView);
        // Adapter to add data in listView
        myList.setAdapter(myAdapter = new MyListAdapter());
        // Make it clickable
        myList.setOnItemLongClickListener(
                // Create a Dialog
            (parent, view, position, id) -> {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle(getResources().getString(R.string.alert_title))

                // Message
                .setMessage(getResources().getString(R.string.alert_msg1) + position + "\n"
                + getResources().getString(R.string.alert_msg2) + id)

                // Yes Action
                .setPositiveButton(R.string.yes, (click, arg) -> {
                    elements.remove(position);
                    myAdapter.notifyDataSetChanged();
                })

                // No action
                .setNegativeButton(R.string.no, (click, arg) -> { })

                //Show the dialog
                .create().show();
                return true;
        } );

        // Whenever you swipe down on the list, do something:
        SwipeRefreshLayout refresher = findViewById(R.id.refresher);
        refresher.setOnRefreshListener( () -> refresher.setRefreshing(false)  );

        // Add chat text
        EditText myChat = findViewById(R.id.chatText);

        // Add Send Button
        Button sendTextButton = findViewById(R.id.sendButton);
        sendTextButton.setOnClickListener(click -> {
            curMsg = new Message(myChat.getText().toString(), "Send");
            elements.add(curMsg);
            myAdapter.notifyDataSetChanged();
            myChat.setText("");
        });

        //Add Rx Button
        Button rxTextButton = findViewById(R.id.rxButton);
        rxTextButton.setOnClickListener(click -> {
            curMsg = new Message(myChat.getText().toString(), "Rx");
            elements.add(curMsg);
            myAdapter.notifyDataSetChanged();
            myChat.setText("");
        });

    }

private class MyListAdapter extends BaseAdapter {
   // Return the number of items
    @Override
    public int getCount() {
        return elements.size();
    }

    // Returns what to show at which row position
    @Override
    public Object getItem(int position) {
        return elements.get(position);
    }

    // Returns the database ID of the object at position i
    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    // Creates a view object to go in a row of the listView
    @Override
    public View getView(int position, View old, ViewGroup parent) {
        View newView = old;
        LayoutInflater inflater = getLayoutInflater();

        // make a new row
        newView = inflater.inflate(elements.get(position).getLayout(), parent, false);

        //set text for new row
        TextView tView = newView.findViewById(elements.get(position).getTextId());
        tView.setText(elements.get(position).msgText);

        // return new row to be added to table
        return newView;
    }
}

// Message class holds the text, ID of the chat box and layout ID for a given message.
private class Message {
        String msgText;
        String msgType;

    public Message (String msgText, String msgType){
        this.msgText = msgText;
        this.msgType = msgType;
    }

    public int getLayout(){
        int layout;
        if (msgType.equals("Send")){
            layout = R.layout.send_row_layout;
        }
        else if (msgType.equals("Rx")){
            layout = R.layout.rx_row_layout;
        }
        else {
            layout = 0;
        }
        return layout;
    }

    public int getTextId(){
        int textID;
        if (msgType == "Send"){
            textID = R.id.sendTextGoesHere;
        }
        else if (msgType == "Rx"){
            textID = R.id.rxTextGoesHere;
        }
        else {
            textID = 0;
        }
        return textID;
    }
}

}