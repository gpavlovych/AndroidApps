package com.example.george.redtubesearch.Preference;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by George on 10/21/2015.
 */
public interface EntryProvider {
    CharSequence[] entries();
    CharSequence[] entryValues();
}
