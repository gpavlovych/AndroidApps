package com.example.george.redtubesearch.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Filter;
import android.widget.Filterable;

import com.commonsware.cwac.endless.EndlessAdapter;
import com.example.george.redtubesearch.Tasks.DownloadXmlTask;
import com.example.george.redtubesearch.Helpers.ArrayHelper;
import com.example.george.redtubesearch.Contract.Star;
import com.example.george.redtubesearch.Contract.StarsList;
import com.example.george.redtubesearch.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 10/11/2015.
 */
public class PornstarsEndlessAdapter extends EndlessAdapter implements Filterable {
    // TODO: 11/18/2015 investigate why it took so much time
    private static final String REDTUBE_DETAILED_STARLIST_URL = "http://api.redtube.com/?data=redtube.Stars.getStarDetailedList&output=xml";
    private final String[] selectedStars;
    private View pendingView = null;
    private RotateAnimation rotate=null;

    public PornstarsEndlessAdapter(Context context, ArrayList<Star> list, String[] selectedStars) {
        super(new StarsListAdapter(context,list));
        this.selectedStars = selectedStars;
        rotate=new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        rotate.setDuration(600);
        rotate.setRepeatMode(Animation.RESTART);
        rotate.setRepeatCount(Animation.INFINITE);
    }

    private List<Star> _items;
    void startProgressAnimation() {
        if (pendingView!=null) {
            pendingView.startAnimation(rotate);
        }
    }
    @Override
    protected View getPendingView(ViewGroup parent){
        View row= LayoutInflater.from(parent.getContext()).inflate(R.layout.thumblist, null);

        pendingView=row.findViewById(R.id.icon);
        pendingView.setVisibility(View.GONE);
        pendingView=row.findViewById(R.id.itemName);
        pendingView.setVisibility(View.GONE);
        pendingView=row.findViewById(R.id.throbber);
        pendingView.setVisibility(View.VISIBLE);
        startProgressAnimation();

        return(row);
    }
    @Override
    protected boolean cacheInBackground() throws Exception {
        try {
            String url = REDTUBE_DETAILED_STARLIST_URL;
            _items=new DownloadXmlTask<StarsList>(StarsList.class).execute(url).get().getStars();
        } catch (Exception e) {
            _items =  null;
        }
        return false;
    }

    @Override
    protected void appendCachedData() {
        StarsListAdapter a = (StarsListAdapter) getWrappedAdapter();
        if (_items != null) {
            for (Star star : _items) {
                star.setSelected(ArrayHelper.containsItem(selectedStars, star.getName()));
                a.add(star);
            }
        }
    }

    @Override
    public Filter getFilter() {
        return ((Filterable)getWrappedAdapter()).getFilter();
    }
}
