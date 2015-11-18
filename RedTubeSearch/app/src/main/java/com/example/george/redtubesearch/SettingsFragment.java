package com.example.george.redtubesearch;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.example.george.redtubesearch.Contract.CategoryList;
import com.example.george.redtubesearch.Contract.StarsList;
import com.example.george.redtubesearch.Contract.TagList;
import com.example.george.redtubesearch.Preference.DynamicFilteredMultiListPreference;
import com.example.george.redtubesearch.Preference.DynamicListPreference;
import com.example.george.redtubesearch.Preference.DynamicMultiListPreference;
import com.example.george.redtubesearch.Preference.EntryProvider;
import com.example.george.redtubesearch.Tasks.DownloadXmlTask;

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
    }
}
