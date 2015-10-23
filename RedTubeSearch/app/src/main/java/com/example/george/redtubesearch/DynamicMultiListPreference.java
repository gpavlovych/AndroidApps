package com.example.george.redtubesearch;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.preference.MultiSelectListPreference;
import android.util.AttributeSet;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by George on 10/21/2015.
 */
public class DynamicMultiListPreference extends MultiSelectListPreference {
    private EntryProvider mEntryProvider;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DynamicMultiListPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DynamicMultiListPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DynamicMultiListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public DynamicMultiListPreference(Context context) {
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
