package com.example.mobilecomputing1;

import android.provider.BaseColumns;

public class DbClass {
    private DbClass() {
    }

    /* Inner class that defines the table contents */
    public static class Notes implements BaseColumns {
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTEXT = "context";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + Notes.TABLE_NAME + " (" +
                        Notes._ID + " INTEGER PRIMARY KEY," +
                        Notes.COLUMN_NAME_TITLE + " TEXT," +
                        Notes.COLUMN_NAME_CONTEXT + " TEXT)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + Notes.TABLE_NAME;
    }
}