package com.example.mobilecomputing1;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "PersonalNotes.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DbClass.Notes.SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DbClass.Notes.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + DbClass.Notes.TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }
    public Cursor getID(String title){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT "+ DbClass.Notes._ID+" FROM "+ DbClass.Notes.TABLE_NAME+" WHERE "+ DbClass.Notes.COLUMN_NAME_TITLE+
                " = '"+title+"'";
        Cursor data = db.rawQuery(query,null);
        return data;
    }
    public Cursor getContent(String title){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT "+ DbClass.Notes.COLUMN_NAME_CONTEXT+" FROM "+ DbClass.Notes.TABLE_NAME+" WHERE "+ DbClass.Notes.COLUMN_NAME_TITLE+
                " = '"+title+"'";
        Cursor data = db.rawQuery(query,null);
        return data;
    }
    public void updateNote(String newTitle, String newContext, int id, String oldTitle){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE "+ DbClass.Notes.TABLE_NAME+" SET "+ DbClass.Notes.COLUMN_NAME_TITLE+
                " = '"+newTitle+"', " + DbClass.Notes.COLUMN_NAME_CONTEXT+" = '"+newContext+"' WHERE "+ DbClass.Notes._ID + " = '"+
                id+"'"+" AND "+ DbClass.Notes.COLUMN_NAME_TITLE + " = '"+ oldTitle+"'";
        db.execSQL(query);
    }
    public void deleteNote(int id, String title){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+ DbClass.Notes.TABLE_NAME+" WHERE "+ DbClass.Notes._ID+
                " = '"+id+"'"+" AND "+ DbClass.Notes.COLUMN_NAME_TITLE + " = '"+title+"'";
        db.execSQL(query);
    }
}
