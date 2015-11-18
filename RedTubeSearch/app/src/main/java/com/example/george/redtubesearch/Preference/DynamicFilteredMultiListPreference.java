package com.example.george.redtubesearch.Preference;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.DialogPreference;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.george.redtubesearch.Contract.Star;
import com.example.george.redtubesearch.Adapters.PornstarsEndlessAdapter;
import com.example.george.redtubesearch.R;
import com.example.george.redtubesearch.SettingsActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by George on 11/8/2015.
 */
public class DynamicFilteredMultiListPreference extends DialogPreference {
    private EntryProvider mEntryProvider;
    private PornstarsEndlessAdapter adapter;

    public void setEntryProvider(EntryProvider provider) {
        mEntryProvider = provider;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DynamicFilteredMultiListPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setDialogLayoutResource(R.layout.dynamic_filtered_multi_list_preference);
    }

    public DynamicFilteredMultiListPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setDialogLayoutResource(R.layout.dynamic_filtered_multi_list_preference);
    }

    public DynamicFilteredMultiListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDialogLayoutResource(R.layout.dynamic_filtered_multi_list_preference);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DynamicFilteredMultiListPreference(Context context) {
        super(context);
        setDialogLayoutResource(R.layout.dynamic_filtered_multi_list_preference);
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        SharedPreferences preferences = getSharedPreferences();
        String[] selectedStars = preferences.getStringSet(SettingsActivity.PREF_KEY_STAR, new HashSet<String>()).toArray(new String[0]);
        adapter = new PornstarsEndlessAdapter(view.getContext(), new ArrayList<Star>(), selectedStars);
        EditText filter = (EditText) view.findViewById(R.id.pornstarFilter);
        ListView list = (ListView) view.findViewById(R.id.pornstarListView);
        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        list.setAdapter(adapter);

    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (!positiveResult)
            return;
        Set<String> selectedStars = new HashSet<String>();
        for (int index = 0; index < adapter.getCount() - 1; index++) {
            Star item = (Star) adapter.getItem(index);
            if (item.getSelected()) {
                selectedStars.add(item.getName());
            }
        }
        SharedPreferences.Editor editor = getEditor();
        editor.putStringSet(SettingsActivity.PREF_KEY_STAR, selectedStars);
        editor.commit();
        super.onDialogClosed(positiveResult);
    }
}
