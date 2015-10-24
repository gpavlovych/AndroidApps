package com.example.george.redtubesearch;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.george.redtubesearch.Contract.VideoItem;

import java.util.ArrayList;

/**
 * Created by George on 10/4/2015.
 */
public class VideosListAdapter extends ArrayAdapter<VideoItem> {

    private final Activity context;

    public VideosListAdapter(Activity context, ArrayList<VideoItem> items) {
        super(context, R.layout.thumblist, items);
        // TODO Auto-generated constructor stub

        this.context = context;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.thumblist, null, true);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView itemName = (TextView) rowView.findViewById(R.id.itemName);

        itemName.setText(getItem(position).getTitle());
        new BitmapDownloader(imageView).execute(getItem(position).getThumbUrl());
        return rowView;

    }
}