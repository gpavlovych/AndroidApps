package com.example.george.redtubesearch;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by George on 10/11/2015.
 */
public class SettingsActivity extends AppCompatActivity {
    public final static String PREF_KEY_SORTING_CATEGORY = "pref_key_sorting_category";
    public final static String PREF_KEY_SORTING = "pref_key_sorting";
    public final static String PREF_KEY_SORTING_METHOD = "pref_key_sorting_method";
    public final static String PREF_KEY_SORTING_PARAMETER="pref_key_sorting_parameter";
    public final static String PREF_KEY_TAG = "pref_key_tag";
    public final static String PREF_KEY_CATEGORY = "pref_key_category";
    public static final String PREF_KEY_HASCATEGORY = "pref_key_hascategory";
    public static final String PREF_KEY_STAR = "pref_key_star";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();


    }
}
