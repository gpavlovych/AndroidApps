package com.example.george.redtubesearch.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.george.redtubesearch.Contract.VideoItem;
import com.example.george.redtubesearch.R;

import java.util.ArrayList;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by George on 10/4/2015.
 */
public class VideosListAdapter extends ArrayAdapter<VideoItem> {
    private final Activity context;

    public VideosListAdapter(Activity context, ArrayList<VideoItem> items) {
        super(context, R.layout.thumblist, items);
        this.context = context;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.thumblist, null, true);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView itemName = (TextView) rowView.findViewById(R.id.itemName);

        itemName.setText(getItem(position).getTitle());
        String url = getItem(position).getThumbUrl();
        Picasso.with(context)
                .load(url)
                .tag(context)
                .into(imageView);
        return rowView;

    }
}