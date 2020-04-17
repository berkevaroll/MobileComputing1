package com.example.mobilecomputing1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditMarker extends AppCompatActivity implements View.OnClickListener {

    private EditText mtitle, desc;
    private Button edit, remove, save;
    private Double mLng;
    private Double mLat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_marker);

        mtitle = (EditText) findViewById(R.id.et_title);
        desc = (EditText) findViewById(R.id.et_desc);
        edit = (Button) findViewById(R.id.marker_edit);
        remove = (Button) findViewById(R.id.marker_remove);
        save = (Button) findViewById(R.id.marker_save);
        desc.setSelection(0);
        Toast.makeText(this, SharedPrefManager.getInstance(this).GetID(), Toast.LENGTH_SHORT).show();
        save.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!= null){
            mLng = (Double)b.get("lng");
            mLat = (Double) b.get("lat");
        }


    }

    private void SaveMarker(){
        final String title = mtitle.getText().toString();
        final String description = desc.getText().toString();
        final String user_id = SharedPrefManager.getInstance(this).GetID();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_ADD_MARKER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(!jsonObject.getBoolean("error"))
                            {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                            }
                            else{
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("title",title);
                params.put("description",description);
                params.put("user_id",user_id);
                params.put("lng",String.valueOf(mLng));
                params.put("lat",String.valueOf(mLat));

                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }
    @Override
    public void onClick(View v) {
        if(v == save){
            Toast.makeText(this, "Tikladin", Toast.LENGTH_SHORT).show();
            SaveMarker();
        }
        if(v == edit){
            //Edit details
        }
        if(v == remove){
            //Remove marker
        }
    }
}
