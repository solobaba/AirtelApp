package com.example.mighty.airtelapp.JobService;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

class PreferenceUtilities {

    public static final String KEY_REFRESHING_REMINDER_COUNT = "refreshing_reminder";
    public static final int DEFAULT_COUNT = 0;

    synchronized public static void incrementRefreshingReminder(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int refreshCount = preferences.getInt(KEY_REFRESHING_REMINDER_COUNT, DEFAULT_COUNT);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_REFRESHING_REMINDER_COUNT, ++refreshCount);
        editor.apply();
    }
}
