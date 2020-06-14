package com.example.mobilecomputing1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FormActivity extends AppCompatActivity {

    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    Button btnsave;
    EditText _title, _content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        openHelper = new DbHelper(this);
        btnsave = (Button) findViewById(R.id.btnsave);
        _title = (EditText) findViewById(R.id.text_title);
        _content = (EditText) findViewById(R.id.text_content);
    }
    public void save(View view){

        db = openHelper.getWritableDatabase();
        String title = _title.getText().toString();
        String content = _content.getText().toString();
        boolean insertData = insertdata(title,content);
        if(insertData) Toast.makeText(this,"Successfull",Toast.LENGTH_SHORT).show();
        else Toast.makeText(this,"Went Wrong!",Toast.LENGTH_SHORT).show();
        finish();
    }
    public boolean insertdata(String title, String content){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbClass.Notes.COLUMN_NAME_TITLE, title );
        contentValues.put(DbClass.Notes.COLUMN_NAME_CONTEXT, content);
        long result = db.insert(DbClass.Notes.TABLE_NAME, null, contentValues);

        if(result == -1){
            return false;

        }
        else{
            return true;
        }
    }
}
