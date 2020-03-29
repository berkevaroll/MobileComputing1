package com.example.mobilecomputing1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;

public class TableActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    ListView listView;
    DbHelper dbHelper;
    PopupMenu popup;
    String _title;
    String _content;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.opt_menu, menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        listView = (ListView)findViewById(R.id.listView);
        dbHelper = new DbHelper(this);
        populateListView();
    }

    @Override
    protected void onResume() {
            super.onResume();
            populateListView();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.add:
                Intent intent = new Intent(this, FormActivity.class);
                startActivity(intent);
                return true;
                default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void populateListView(){

        Cursor data = dbHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(1));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listData);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            showPopup(view);
            _title = adapterView.getItemAtPosition(i).toString();
            return false;
            }
        });

    }
    public void showPopup(View v) {
        popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(this);
    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Cursor data2 = dbHelper.getContent(_title);
                Cursor data = dbHelper.getID(_title);
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                //_content = data2.getString(2);
                Intent editScreenIntent = new Intent(TableActivity.this, EditDataActivity.class);
                editScreenIntent.putExtra("id",itemID);
                editScreenIntent.putExtra("content",_content);
                editScreenIntent.putExtra("title",_title);
                startActivity(editScreenIntent);
                return true;
            case R.id.delete:
                data = dbHelper.getID(_title);
                itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
               dbHelper.deleteNote(itemID,_title);
                onResume();
                return true;
            default:
                return false;
        }
    }
}
