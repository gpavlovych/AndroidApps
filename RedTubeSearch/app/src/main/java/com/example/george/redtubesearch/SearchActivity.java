package com.example.george.redtubesearch;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.commonsware.cwac.endless.EndlessAdapter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public static final String REDTUBE_URL = "http://api.redtube.com/?data=redtube.Videos.searchVideos&output=xml&search=%s&tags[]=MILF&thumbsize=all";

    @Override
    public boolean onQueryTextSubmit(String query) {
        EndlessAdapter adapter = new VideosListEndlessAdapter(this, new ArrayList<VideoItem>(),query);
        ListView list = (ListView) findViewById(R.id.videosList);
        list.setAdapter(adapter);
        final Activity context = this;
        list.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VideosListEndlessAdapter adapter = (VideosListEndlessAdapter)(parent.getAdapter());
                Intent intent = new Intent(context, PlayVideoActivity.class);
                intent.setData(Uri.parse(((VideoItem)adapter.getItem(+position)).videoUrl.replace("player/", "")));
                startActivity(intent);
            }
        });
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private void ShowProgressBar() {
        final ProgressBar contentLoading = (ProgressBar) findViewById(R.id.contentLoading);
        contentLoading.setVisibility(ProgressBar.VISIBLE);
    }

    private void HideProgressBar() {
        final ProgressBar contentLoading = (ProgressBar) findViewById(R.id.contentLoading);
        contentLoading.setVisibility(ProgressBar.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        HideProgressBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        if (searchView != null) {
            searchView.setOnQueryTextListener(this);
        }

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }
}
