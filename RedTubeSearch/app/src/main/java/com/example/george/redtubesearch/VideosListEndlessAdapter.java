package com.example.george.redtubesearch;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ProgressBar;

import com.commonsware.cwac.endless.EndlessAdapter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 10/11/2015.
 */
public class VideosListEndlessAdapter extends EndlessAdapter {
    private final String searchTerm;
    public static final String REDTUBE_URL = "http://api.redtube.com/?data=redtube.Videos.searchVideos&output=xml&search=%s&tags[]=MILF&thumbsize=all&page=%s";
    private View pendingView = null;
    private RotateAnimation rotate=null;

    VideosListEndlessAdapter(Activity activity, ArrayList<VideoItem> list,String searchTerm) {
        super(new VideosListAdapter(activity,list));
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
            // Thread.sleep(10000);
            _items = loadXmlFromNetwork(String.format(REDTUBE_URL,searchTerm,pageNumber));
        } catch (IOException e) {
            _items =  null;
        } catch (XmlPullParserException e) {
            _items =  null;
        }
        return _items!=null && _items.size()>0;
    }
    private List<VideoItem> loadXmlFromNetwork(String url) throws IOException, XmlPullParserException {
        URL urlObj = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
        BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
        XmlPullParser parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(in, null);
        parser.nextTag();
        return readEntries(parser, "root");
    }

    @NonNull
    private List<VideoItem> readEntries(XmlPullParser parser, String startTag) throws XmlPullParserException, IOException {
        List<VideoItem> entries = new ArrayList<VideoItem>();
        parser.require(XmlPullParser.START_TAG, "", startTag);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            switch (name) {
                case "video":
                    entries.add(readEntry(parser));
                    break;
                case "videos":
                    return readEntries(parser, "videos");
                default:
                    skip(parser);
                    break;
            }
        }
        return entries;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private VideoItem readEntry(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, "", "video");
        String title = null;
        String embedUrl = null;
        String link = null;
        embedUrl = parser.getAttributeValue("", "embed_url");
        link = parser.getAttributeValue("", "thumb");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if (name.equals("title")) {
                title = parser.getText();
                if (title == null) {
                    parser.nextToken();
                    title = parser.getText();
                }
                while (parser.next() != XmlPullParser.END_TAG) parser.next();
            } /*else if (name.equals("summary")) {
                    summary = readSummary(parser);
                } else if (name.equals("link")) {
                    link = readLink(parser);
                }*/ else {
                skip(parser);
            }
        }
        return new VideoItem(title, link, embedUrl);
    }

    @Override
    protected void appendCachedData() {
        VideosListAdapter a=(VideosListAdapter)getWrappedAdapter();
        if (_items != null)
        for (VideoItem videoItem : _items) { a.add(videoItem); }
    }
}
