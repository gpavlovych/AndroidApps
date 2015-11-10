package com.example.george.redtubesearch.Preference;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.preference.DialogPreference;
import android.util.AttributeSet;

import com.example.george.redtubesearch.EntryProvider;
import com.example.george.redtubesearch.R;

/**
 * Created by George on 11/8/2015.
 */
public class DynamicFilteredMultiListPreference extends DialogPreference {
    private EntryProvider mEntryProvider;
    public void setEntryProvider(EntryProvider provider)
    {
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

}
