package com.example.mobilecomputing1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditDataActivity extends AppCompatActivity {

    private Button savebtn2;
    private EditText edit_title, edit_context;

    DbHelper dbHelper;

    private String selectedtitle, selectedcontent;
    private int selectedID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_data_layout);
        savebtn2 = (Button)findViewById(R.id.savebtn2);
        edit_title = (EditText)findViewById(R.id.edittitle);
        edit_context = (EditText)findViewById(R.id.editcontent);
        dbHelper = new DbHelper(this);

        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getIntExtra("id",-1);
        selectedtitle = receivedIntent.getStringExtra("title");
        selectedcontent = receivedIntent.getStringExtra("content");

        edit_title.setText(selectedtitle);
        edit_context.setText(selectedcontent);

        savebtn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String title_ = edit_title.getText().toString();
                String content_ = edit_context.getText().toString();
                dbHelper.updateNote(title_,content_,selectedID, selectedtitle);
                finish();
            }
        });
    }
}
