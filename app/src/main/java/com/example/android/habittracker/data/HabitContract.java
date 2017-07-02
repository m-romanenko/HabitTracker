package com.example.android.habittracker.data;

import android.provider.BaseColumns;

public final class HabitContract {

    public static abstract class HabitEntry implements BaseColumns {

        public static final String TABLE_NAME = "habits";
        public final static String _ID = BaseColumns._ID;
        public static final String COLUMN_HABIT_NAME = "name";
        public static final String COLUMN_HABIT_DURATION = "duration";
    }
}
