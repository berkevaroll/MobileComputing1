package com.example.mobilecomputing1;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class dataSource {
    SQLiteDatabase db;
    sqlite_level mydb;

    public dataSource(Context c){mydb = new sqlite_level(c);}

    public void open(){db = mydb.getWritableDatabase();}

    public void close(){ mydb.close();}

    public void createData(Data d){
        ContentValues val = new ContentValues();
        val.put("about",d.getAbout());
        db.insert("datas", null, val);
    }

    public void deleteData(Data d){
        int id = d.getId();
        db.delete("datas", "_id="+id,null);
    }

    public List<Data> List(){
        String columns[] ={"_id","about"};
        List<Data> list = new ArrayList<Data>();
        Cursor c = db.query("datas", columns, null,null,null,null,null);
        c.moveToFirst();
        while (!c.isLast()){
            int id = c.getInt(0);
            String about = c.getString(1);
            Data d = new Data(id,about);
            list.add(d);
            c.moveToNext();
        }

        return list;
    }
}
