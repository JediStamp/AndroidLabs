package com.example.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//import androidx.annotation.Nullable;

public class MyDBOpener extends SQLiteOpenHelper {
    protected final static String DATABASE_NAME = "ChatRoomDB";
    protected final static int VERSION_NUM = 1;
    public final static String TABLE_NAME = "Message";
    public final static String COL_MSG = "MSG_TEXT";
    public final static String COL_TYPE = "MSG_TYPE";
    public final static String COL_ID = "_id";

    // Constructor
    public MyDBOpener(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    // This function gets called if no database file exists.
    // Look on your device in the /data/data/package-name/database directory.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_MSG + " text,"
                + COL_TYPE  + " text);");
    }

    // This function gets called if the database version on your device is lower than VERSION_NUM
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create the new table:
        onCreate(db);
    }

    // This function gets called if the database version on your device is higher than VERSION_NUM
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create the new table:
        onCreate(db);
    }

    // Open the database
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
