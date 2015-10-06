package com.example.george.redtubesearch;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by George on 10/4/2015.
 */
public class VideosListAdapter extends ArrayAdapter<VideoItem> {

    private final Activity context;
    private final VideoItem[] items;

    public VideosListAdapter(Activity context, VideoItem[] items) {
        super(context, R.layout.thumblist, items);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.items = items;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.thumblist, null, true);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView itemName = (TextView) rowView.findViewById(R.id.itemName);

        itemName.setText(items[position].title);
        new BitmapDownloader(imageView).execute(items[position].thumbUrl);
        return rowView;

    }
}