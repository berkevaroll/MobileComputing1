package com.example.mobilecomputing1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;

public class Table extends ListActivity {
    dataSource ds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        ds = new dataSource(this);
        ds.open();
        List<Data> datas = ds.List();
        final ArrayAdapter<Data> adapter = new ArrayAdapter<Data>(this, android.R.layout.simple_list_item_1, datas);
        setListAdapter(adapter);

        Button add = (Button) findViewById(R.id.addButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data d = new Data(1, "Construction!");
                ds.createData(d);
                adapter.add(d);
            }
        });
        Button delete = (Button) findViewById(R.id.deleteButton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data d = (Data) getListAdapter().getItem(0);
                ds.deleteData(d);
                adapter.remove(d);
            }
        });


    }
    protected void onResume(){
        ds.open();
        super.onResume();
    }
    protected void onPause(){
        ds.close();
        super.onPause();
    }
}
