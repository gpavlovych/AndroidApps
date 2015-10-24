package com.example.george.redtubesearch;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.commonsware.cwac.endless.EndlessAdapter;
import com.example.george.redtubesearch.Contract.VideoItem;
import com.example.george.redtubesearch.Contract.VideoList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 10/11/2015.
 */
public class VideosListEndlessAdapter extends EndlessAdapter {
    private final String searchTerm;
    public static final String REDTUBE_URL = "http://api.redtube.com/?data=redtube.Videos.searchVideos&output=xml&search=%s&thumbsize=all&page=%s";
    private static final String REDTUBE_CATEGORY_URL="&category=%s";
    private static final String REDTUBE_TAGS_URL="&tags[]=%s";
    private static final String REDTUBE_STARS_URL="&stars[]=%s";
    private final boolean isSorted;
    private final String sortingMethod;
    private final String sortingParameter;
    private final boolean hasCategory;
    private final String category;
    private final String tags;
    private final String stars;
    private View pendingView = null;
    private RotateAnimation rotate=null;

    VideosListEndlessAdapter(Activity activity, ArrayList<VideoItem> list, String searchTerm, boolean isSorted, String sortingMethod, String sortingParameter,boolean hasCategory, String category, String tags,String stars) {
        super(new VideosListAdapter(activity,list));
        this.isSorted = isSorted;
        this.sortingMethod = sortingMethod;
        this.sortingParameter = sortingParameter;
        this.hasCategory = hasCategory;
        this.category = category;
        this.tags = tags;
        this.stars = stars;
        rotate=new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        rotate.setDuration(600);
        rotate.setRepeatMode(Animation.RESTART);
        rotate.setRepeatCount(Animation.INFINITE);
        this.searchTerm = searchTerm;
    }
    private int pageNumber = 0;
    private List<VideoItem> _items;
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
        pageNumber++;
        try {
            String url = (String.format(REDTUBE_URL,searchTerm,pageNumber)+
                    (isSorted?String.format("&ordering=%s",sortingMethod):"")+
                    (isSorted && (sortingMethod.equals("rating") || sortingMethod.equals("mostviewed"))?String.format("&period=%s",sortingParameter):"")+
                    (hasCategory?String.format(REDTUBE_CATEGORY_URL, category):"")+
                    ((!TextUtils.isEmpty(tags))?String.format(REDTUBE_TAGS_URL,tags):"")+
                    ((!TextUtils.isEmpty(stars))?String.format(REDTUBE_STARS_URL,stars):""));
            _items=new DownloadXmlTask<VideoList>(VideoList.class).execute(url).get().getVideos();
        } catch (Exception e) {
            _items =  null;
        }
        return _items!=null && _items.size()>0;
    }

    @Override
    protected void appendCachedData() {
        VideosListAdapter a=(VideosListAdapter)getWrappedAdapter();
        if (_items != null)
        for (VideoItem videoItem : _items) { a.add(videoItem); }
    }
}
