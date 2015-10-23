package com.example.george.redtubesearch;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by George on 10/11/2015.
 */
public class SettingsFragment extends PreferenceFragment {

    public static final String REDTUBE_CATEGORIES_API_URL = "http://api.redtube.com/?data=redtube.Categories.getCategoriesList&output=xml";
    public static final String REDTUBE_TAGS_API_URL="http://api.redtube.com/?data=redtube.Tags.getTagList&output=xml";
    private static final String REDTUBE_STARS_API_URL = "http://api.redtube.com/?data=redtube.Stars.getStarList&output=xml";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        final ListPreference sortingMethodList = (ListPreference)findPreference("pref_key_sorting_method");
        final ListPreference sortingParameterList = (ListPreference)findPreference("pref_key_sorting_parameter");
        sortingMethodList.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                final String val = newValue.toString();
                sortingParameterList.setEnabled(("mostviewed".equals(val) || "rating".equals(val)));
                return true;
            }
        });
        sortingParameterList.setEnabled(("mostviewed".equals(sortingMethodList.getValue()) || "rating".equals(sortingMethodList.getValue())));
        DynamicListPreference categoryListPreference = (DynamicListPreference)findPreference("pref_key_category");
        categoryListPreference.setEntryProvider(new EntryProvider() {
            @Override
            public CharSequence[] entries() {
                try {
                    return new DownloadXmlTask<CategoryList>(CategoryList.class).execute(REDTUBE_CATEGORIES_API_URL).get().getCategories().toArray(new String[0]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return new CharSequence[0];
            }

            @Override
            public CharSequence[] entryValues() {

                try {
                    return new DownloadXmlTask<CategoryList>(CategoryList.class).execute(REDTUBE_CATEGORIES_API_URL).get().getCategories().toArray(new String[0]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return new CharSequence[0];
            }
        });

        DynamicMultiListPreference tagListPreference = (DynamicMultiListPreference)findPreference("pref_key_tag");
        tagListPreference.setEntryProvider(new EntryProvider() {
            @Override
            public CharSequence[] entries() {
                try {
                    return new DownloadXmlTask<TagList>(TagList.class).execute(REDTUBE_TAGS_API_URL).get().getTags().toArray(new String[0]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return new CharSequence[0];
            }

            @Override
            public CharSequence[] entryValues() {
                try {
                    return new DownloadXmlTask<TagList>(TagList.class).execute(REDTUBE_TAGS_API_URL).get().getTags().toArray(new String[0]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return new CharSequence[0];
            }
        });
        DynamicMultiListPreference starListPreference = (DynamicMultiListPreference)findPreference("pref_key_star");
        starListPreference.setEntryProvider(new EntryProvider() {
            @Override
            public CharSequence[] entries() {
                try {
                    return new DownloadXmlTask<StarsList>(StarsList.class).execute(REDTUBE_STARS_API_URL).get().getStars().toArray(new String[0]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return new CharSequence[0];
            }

            @Override
            public CharSequence[] entryValues() {
                try {
                    return new DownloadXmlTask<StarsList>(StarsList.class).execute(REDTUBE_STARS_API_URL).get().getStars().toArray(new String[0]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return new CharSequence[0];
            }
        });
    }
}
