package com.example.mobilecomputing1;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyListAdapter extends ArrayAdapter<String> {

    private Context mcontext;
    int mresource;

    public MyListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        mcontext=context;
        mresource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        setupImageLoader();
        String title = getItem(position);
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        convertView = inflater.inflate(mresource,parent,false);

        TextView title_view=(TextView) convertView.findViewById(R.id.textView1);
        TextView date_view = (TextView) convertView.findViewById(R.id.textView2);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
        ImageLoader imageLoader = ImageLoader.getInstance();
        String imgurl = "drawable://" + R.drawable.image_default;

        int defaultImage = mcontext.getResources().getIdentifier("@drawable/image_failed", null, mcontext.getPackageName());
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .showImageOnLoading(defaultImage).build();
        imageLoader.displayImage(imgurl,imageView,options);


        title_view.setText(title);
        date_view.setText(date);
        return convertView;
    }

    private void setupImageLoader(){
        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mcontext)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP
    }
}
