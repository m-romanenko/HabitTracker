package com.example.android.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.habittracker.data.HabitContract;
import com.example.android.habittracker.data.HabitContract.HabitEntry;

import com.example.android.habittracker.data.HabitDbHelper;

public class MainActivity extends AppCompatActivity {

    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new HabitDbHelper(this);
        insertHabits();
        Cursor cursor = readData();
        displayDatabaseInfo(cursor);
    }

    private Cursor readData(){
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_NAME,
                HabitEntry.COLUMN_HABIT_DURATION,
        };
        //query database
        Cursor cursor = db.query(HabitContract.HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        return cursor;
    }

    private void displayDatabaseInfo(Cursor cursor) {
        TextView displayView = (TextView) findViewById(R.id.txt_query_results);
        try {
            displayView.append(HabitEntry._ID + " - " +
                    HabitEntry.COLUMN_HABIT_NAME + " - " +
                    HabitEntry.COLUMN_HABIT_DURATION + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_NAME);
            int durationColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_DURATION);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentDuration = cursor.getInt(durationColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentDuration));
            }
        } finally {
            cursor.close();
        }
    }

    private void insertHabits() {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        values.put(HabitEntry.COLUMN_HABIT_NAME, getString(R.string.work_out));
        values.put(HabitEntry.COLUMN_HABIT_DURATION, 30);
        // Insert the new row
        db.insert(HabitEntry.TABLE_NAME, null, values);

        values.put(HabitEntry.COLUMN_HABIT_NAME, getString(R.string.wash_dishes));
        values.put(HabitEntry.COLUMN_HABIT_DURATION, 5);
        db.insert(HabitEntry.TABLE_NAME, null, values);

        values.put(HabitEntry.COLUMN_HABIT_NAME, getString(R.string.do_homework));
        values.put(HabitEntry.COLUMN_HABIT_DURATION, 70);
        db.insert(HabitEntry.TABLE_NAME, null, values);
    }
}
