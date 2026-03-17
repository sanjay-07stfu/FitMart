package com.fitmart.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsManager {
    private static final String PREF_NAME = "fitmart_prefs";
    private static SharedPrefsManager instance;
    private SharedPreferences prefs;

    private SharedPrefsManager(Context context) {
        prefs = context.getApplicationContext()
                       .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPrefsManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefsManager(context);
        }
        return instance;
    }

    public void setLoggedIn(boolean loggedIn) {
        prefs.edit().putBoolean(Constants.PREF_IS_LOGGED_IN, loggedIn).apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(Constants.PREF_IS_LOGGED_IN, false);
    }

    public void saveUserData(String uid, String name, String email, String phone) {
        prefs.edit()
             .putString(Constants.PREF_USER_UID, uid)
             .putString(Constants.PREF_USER_NAME, name)
             .putString(Constants.PREF_USER_EMAIL, email)
             .putString(Constants.PREF_USER_PHONE, phone)
             .apply();
    }

    public String getUserName()  { return prefs.getString(Constants.PREF_USER_NAME, ""); }
    public String getUserEmail() { return prefs.getString(Constants.PREF_USER_EMAIL, ""); }
    public String getUserPhone() { return prefs.getString(Constants.PREF_USER_PHONE, ""); }
    public String getUserUid()   { return prefs.getString(Constants.PREF_USER_UID, ""); }

    public void clearAll() {
        prefs.edit().clear().apply();
    }
}
