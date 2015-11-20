package com.example.george.redtubesearch.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.george.redtubesearch.Contract.Star;
import com.example.george.redtubesearch.R;

import java.util.ArrayList;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by George on 10/4/2015.
 */
public class StarsListAdapter extends ArrayAdapter<Star> {

    private final Context context;

    public StarsListAdapter(Context context, ArrayList<Star> items) {
        super(context, R.layout.thumbchecklist, items);
        this.context = context;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView = inflater.inflate(R.layout.thumbchecklist, null, true);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView itemName = (TextView) rowView.findViewById(R.id.itemName);
        CheckBox itemSelected = (CheckBox)rowView.findViewById(R.id.itemSelected);
        itemSelected.setTag(getItem(position));
        itemSelected.setChecked(getItem(position).getSelected());
        itemSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((Star)buttonView.getTag()).setSelected(isChecked);
            }
        });
        itemName.setText(getItem(position).getName());
        String url = getItem(position).getThumbUrl();
        Picasso.with(context)
                .load(url)
                .tag(context)
                .into(imageView);
        return rowView;
    }
}