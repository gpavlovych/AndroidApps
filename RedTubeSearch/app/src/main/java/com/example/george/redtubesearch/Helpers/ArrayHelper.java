package com.example.george.redtubesearch.Helpers;

import android.text.TextUtils;

/**
 * Created by George on 11/15/2015.
 */
public class ArrayHelper {
    public static boolean containsItem(String[] array, String arrayItem) {
        for (int index = 0; index< array.length; index++) {
            if (TextUtils.equals(array[index], arrayItem)) {
                return true;
            }
        }
        return false;
    }
}
