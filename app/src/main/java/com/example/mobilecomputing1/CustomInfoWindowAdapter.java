package com.example.mobilecomputing1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{

    private final View mWindow;
    private Context mContext;

    public CustomInfoWindowAdapter(Context context) {
        this.mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window_auth,null);

    }
    private void setText(Marker marker, View view){
        String description = "";
        Intent intent = ((Activity) mContext).getIntent();
        Bundle b = intent.getExtras();
        if(b!= null){
             description = (String) b.getString("desc_");
        }
        String title = marker.getTitle();
        TextView tvTitle = (TextView) view.findViewById(R.id.title_auth);

        if(!title.equals("")){
            tvTitle.setText(title);
        }
        TextView tvSnippet = (TextView) view.findViewById(R.id.description_auth);
        if(!description.equals("")){
            tvSnippet.setText(description);
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        setText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        setText(marker, mWindow);
        return mWindow;
    }
}
