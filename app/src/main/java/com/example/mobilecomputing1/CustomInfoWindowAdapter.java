package com.example.mobilecomputing1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{

    private final View mWindow;
    private Context mContext;

    public CustomInfoWindowAdapter(Context context) {
        this.mContext = mContext;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window_auth,null);
    }
    private void setText(Marker marker, View view){

        String title = marker.getTitle();
        TextView tvTitle = (TextView) view.findViewById(R.id.title_auth);

        if(!title.equals("")){
            tvTitle.setText(title);
        }

        String description = "dsfjghdsjfgkhdlsfglsdfkjghfsdjbpgjsdf" +
                "dsfjghdsjfgkhdlsfglsdfkjghfsdjbpgjsdf" +
                "dsfjghdsjfgkhdlsfglsdfkjghfsdjbpgjsdf" +
                "dsfjghdsjfgkhdlsfglsdfkjghfsdjbpgjsdf" +
                "dsfjghdsjfgkhdlsfglsdfkjghfsdjbpgjsdf" +
                "dsfjghdsjfgkhdlsfglsdfkjghfsdjbpgjsdf" +
                "dsfjghdsjfgkhdlsfglsdfkjghfsdjbpgjsdf";
        TextView tvSnippet = (TextView) view.findViewById(R.id.description_auth);

        if(!title.equals("")){
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
