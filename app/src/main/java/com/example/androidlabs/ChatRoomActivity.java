package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
    private ArrayList<String> elements = new ArrayList<>();
    private MyListAdapter myAdapter;
    private Message curMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        // Create a List View an make it clickable
        ListView myList = findViewById(R.id.theListView);
        myList.setAdapter(myAdapter = new MyListAdapter());
        myList.setOnItemLongClickListener(
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

        //Whenever you swipe down on the list, do something:
        SwipeRefreshLayout refresher = findViewById(R.id.refresher);
        refresher.setOnRefreshListener( () -> refresher.setRefreshing(false)  );

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