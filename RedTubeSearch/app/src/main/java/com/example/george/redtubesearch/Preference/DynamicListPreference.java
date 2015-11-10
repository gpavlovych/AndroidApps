package com.example.george.redtubesearch.Preference;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.preference.ListPreference;
import android.util.AttributeSet;

import com.example.george.redtubesearch.EntryProvider;

/**
 * Created by George on 10/22/2015.
 */
public class DynamicListPreference extends ListPreference {
    private EntryProvider mEntryProvider;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DynamicListPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DynamicListPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DynamicListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicListPreference(Context context) {
        super(context);
    }

    public void setEntryProvider(EntryProvider provider)
    {
        mEntryProvider = provider;
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        if (mEntryProvider != null) {
            setEntries(mEntryProvider.entries());
            setEntryValues(mEntryProvider.entryValues());
        }
        super.onPrepareDialogBuilder(builder);
    }
}
