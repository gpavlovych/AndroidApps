package com.example.george.redtubesearch;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.commonsware.cwac.endless.EndlessAdapter;
import com.example.george.redtubesearch.Contract.VideoItem;

import java.util.ArrayList;
import java.util.HashSet;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private static final int RESULT_SETTINGS = 1;

    @Override
    public boolean onQueryTextSubmit(String query) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        boolean isSorted = sharedPref.getBoolean(SettingsActivity.PREF_KEY_SORTING, false);
        String sortingMethod = sharedPref.getString(SettingsActivity.PREF_KEY_SORTING_METHOD, getString(R.string.pref_key_sorting_method_default));
        String sortingParameter = sharedPref.getString(SettingsActivity.PREF_KEY_SORTING_PARAMETER, getString(R.string.pref_key_sorting_parameter_default));
        boolean hasCategory=sharedPref.getBoolean(SettingsActivity.PREF_KEY_HASCATEGORY,false);
        String category = sharedPref.getString(SettingsActivity.PREF_KEY_CATEGORY,"");
        String tags = TextUtils.join(",", sharedPref.getStringSet(SettingsActivity.PREF_KEY_TAG, new HashSet<String>()).toArray(new String[0]));
        String stars = TextUtils.join(",", sharedPref.getStringSet(SettingsActivity.PREF_KEY_STAR, new HashSet<String>()).toArray(new String[0]));
        EndlessAdapter adapter = new VideosListEndlessAdapter(this, new ArrayList<VideoItem>(),query, isSorted, sortingMethod,sortingParameter,hasCategory,category,tags,stars);
        ListView list = (ListView) findViewById(R.id.videosList);
        list.setAdapter(adapter);
        final Activity context = this;
        list.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VideosListEndlessAdapter adapter = (VideosListEndlessAdapter) (parent.getAdapter());
                Intent intent = new Intent(context, PlayVideoActivity.class);
                intent.setData(Uri.parse(((VideoItem) adapter.getItem(+position)).getVideoUrl().replace("player/", "")));
                startActivity(intent);
            }
        });
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        PreferenceManager.setDefaultValues(this,R.xml.preferences,false);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivityForResult(i, RESULT_SETTINGS);
                break;

        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SETTINGS:
                //
                break;
        }
    }
}
