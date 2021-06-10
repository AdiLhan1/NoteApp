package com.eapple.noteapp.unit;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHelper {
    public static final String STORAGE_NAME = "StorageName";

    private static SharedPreferences sharedPreferences = null;
    private static SharedPreferences.Editor editor = null;
    private static Context context = null;

    public static void init(Context cntxt) {
        context = cntxt;
    }

    private static void init() {
        sharedPreferences = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static void addProperty(String key, String value) {
        if (sharedPreferences == null) {
            init();
        }
        editor.putString(key, value);
        editor.apply();
    }

    public static String getProperty(String key) {
        if (sharedPreferences == null) {
            init();
        }
        return sharedPreferences.getString(key, null);
    }
}
