package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
    public static final String ACTIVITY_NAME = "CHAT_ROOM_ACTIVITY";
    private ArrayList<Message> elements = new ArrayList<>(); // Messages
    private MyListAdapter myAdapter;
    private Message curMsg;
    private SQLiteDatabase myDB;
    private Cursor results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        // Load Messages from the Database
        loadMessages();
        printCursor(results, myDB.getVersion());

        // Create a List View
        createListView();
        myAdapter.notifyDataSetChanged();

        // Whenever you swipe down on the list, do something:
        SwipeRefreshLayout refresher = findViewById(R.id.refresher);
        refresher.setOnRefreshListener(() -> refresher.setRefreshing(false));

        // Add chat text
        EditText myChat = findViewById(R.id.chatText);

        // Add Send Button
        Button sendTextButton = findViewById(R.id.sendButton);
        sendTextButton.setOnClickListener(click -> {
            ContentValues newMsgVal = new ContentValues();
            newMsgVal.put(MyDBOpener.COL_MSG, myChat.getText().toString());
            newMsgVal.put(MyDBOpener.COL_TYPE, "Send");

            //Now insert in the database:
            long newID = myDB.insert(MyDBOpener.TABLE_NAME, null, newMsgVal);

            curMsg = new Message(myChat.getText().toString(), "Send", newID);
            elements.add(curMsg);
            myAdapter.notifyDataSetChanged();
            myChat.setText("");
        });

        //Add Rx Button
        Button rxTextButton = findViewById(R.id.rxButton);
        rxTextButton.setOnClickListener(click -> {
            ContentValues newMsgVal = new ContentValues();
            newMsgVal.put(MyDBOpener.COL_MSG, myChat.getText().toString());
            newMsgVal.put(MyDBOpener.COL_TYPE, "Rx");

            //Now insert in the database:
            long newID = myDB.insert(MyDBOpener.TABLE_NAME, null, newMsgVal);
            curMsg = new Message(myChat.getText().toString(), "Rx", newID);
            elements.add(curMsg);
            myAdapter.notifyDataSetChanged();
            myChat.setText("");
        });
    }

    private void createListView() {
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
                                deleteMessage(elements.get(position));
                                elements.remove(position);
                                myAdapter.notifyDataSetChanged();
                            })

                            // No action
                            .setNegativeButton(R.string.no, (click, arg) -> {
                            })

                            //Show the dialog
                            .create().show();
                    return true;
                });
    }

    private void loadMessages() {
        // Connect to DB
        MyDBOpener dbOpener = new MyDBOpener(this);
        myDB = dbOpener.getWritableDatabase();

        // list of columns
        String[] columns = {MyDBOpener.COL_ID, MyDBOpener.COL_MSG, MyDBOpener.COL_TYPE};
        // get all entries
        results = myDB.query(false, MyDBOpener.TABLE_NAME, columns, null,
                null, null, null, null, null);

        // Get column indices
        int idColIndex = results.getColumnIndex(MyDBOpener.COL_ID);
        int msgColIndex = results.getColumnIndex(MyDBOpener.COL_MSG);
        int typeColIndex = results.getColumnIndex(MyDBOpener.COL_TYPE);

        // Iterate over the results, return true if there is a next item:
        while (results.moveToNext()) {
            // Create a message and add it to the arrayList
            curMsg = new Message(results.getString(msgColIndex),
                    results.getString(typeColIndex),
                    results.getLong(idColIndex));
            elements.add(curMsg);
        }
    }

    private void deleteMessage(Message msg) {
        myDB.delete(MyDBOpener.TABLE_NAME, MyDBOpener.COL_ID + "= ?",
                new String[]{Long.toString(msg.msgID)});
    }

    private void printCursor(Cursor c, int version){
        int colCount = c.getColumnCount();
        int numRecords = c.getCount();

        // Database Version Number
        Log.d(ACTIVITY_NAME, "Database version number: " + version);

        // Number of Columns
        Log.d(ACTIVITY_NAME, "The number of columns is: " + colCount);

        // Column Names
        for (int i = 0; i < colCount; i++){
            Log.d(ACTIVITY_NAME, "Column Name is: " + c.getColumnName(i));
        }

        // Number of rows of data
        Log.d(ACTIVITY_NAME, "Number of rows in the DB is: " + numRecords);

        // Display row of data
        c.moveToFirst();
        for (int row = 0; row < numRecords; row++){
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < colCount; i++){
                sb.append(c.getString(i));
                sb.append(", ");
            }
            Log.d(ACTIVITY_NAME, sb.toString());
            c.moveToNext();
        }
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
            LayoutInflater inflater = getLayoutInflater();

            // make a new row
            View newView = inflater.inflate(elements.get(position).getLayout(), parent, false);

            //set text for new row
            TextView tView = newView.findViewById(elements.get(position).getTextId());
            tView.setText(elements.get(position).msgText);

            // return new row to be added to table
            return newView;
        }
    }

}