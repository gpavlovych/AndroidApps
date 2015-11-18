package com.example.george.redtubesearch.Preference;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.preference.MultiSelectListPreference;
import android.util.AttributeSet;
// TODO: 11/10/2015 Set asynchronous settings loading
// TODO: 11/10/2015 Think on EditText behavior (search button, keyboard should be closed etc/
// TODO: 11/10/2015 Think on how filtering in list view can be implemented
// TODO: 11/10/2015 icons might be added (discuss???)
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
