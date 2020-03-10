package com.example.mobilecomputing1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void getTable(View view) {
        Intent intent = new Intent(this, Table.class);
        startActivity(intent);
    }
}
