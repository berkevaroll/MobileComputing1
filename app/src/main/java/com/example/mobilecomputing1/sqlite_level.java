package com.example.mobilecomputing1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class sqlite_level extends SQLiteOpenHelper {

    public sqlite_level(Context c){
        super(c, "datas", null, 7);
    }
    public void onCreate(SQLiteDatabase db){
        String sql = " create table datas ( _id integer primary key autoincrement" +
                ", about text not null)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists datas");
        onCreate(db);
    }
}
