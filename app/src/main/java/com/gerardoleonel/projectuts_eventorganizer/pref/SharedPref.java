package com.gerardoleonel.projectuts_eventorganizer.pref;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

import com.gerardoleonel.projectuts_eventorganizer.db.entity.User;

public class SharedPref {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private static final String PREF_NAME = "EventKuPref";
    private static final String IS_LOGIN = "IsLogin";
    private static final String IS_CHANGED = "IsChanged";
    private static final String USERNAME = "Username";
    private static final String NOHP = "Nohp";
    private static final String ALAMAT = "Alamat";
    private static final String EMAIL = "Email";
    private static final String AVATAR = "UserAvatar";
    private static final String ACTIVITY_THEME = "ActivityTheme";


    public SharedPref(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void createSession(User user) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(USERNAME, user.getUsername());
        editor.putString(ALAMAT, user.getAlamat());
        editor.putString(NOHP,user.getNoHP());
        editor.putString(EMAIL, user.getEmail());
        editor.putString(AVATAR, user.getUserAvatar());
        if(user.getUserAvatar()!=null)
        {
            editor.putBoolean(IS_CHANGED,true);
        }
        editor.commit();
    }

    public String getSessionImage() {
        return preferences.getString(AVATAR, null);
    }

    public String getSessionUsername() {
        return preferences.getString(USERNAME, "");
    }

    public String getSessionAddress(){
        return preferences.getString(ALAMAT,"");
    }

    public String getSessionEmail(){
        return preferences.getString(EMAIL,"");
    }

    public String getSessionPhone(){
        return preferences.getString(NOHP,"");
    }

    public boolean isLogin() {
        return preferences.getBoolean(IS_LOGIN, false);
    }

    public boolean isChanged()
    {
        return preferences.getBoolean(IS_CHANGED, false);
    }

    public int getAppTheme() {
        return preferences.getInt(ACTIVITY_THEME, AppCompatDelegate.MODE_NIGHT_NO);
    }

    public void switchAppTheme() {
        if (getAppTheme() == AppCompatDelegate.MODE_NIGHT_NO) {
            editor.putInt(ACTIVITY_THEME, AppCompatDelegate.MODE_NIGHT_YES);
        }
        else editor.putInt(ACTIVITY_THEME, AppCompatDelegate.MODE_NIGHT_NO);
        editor.apply();
    }

    public void imageCreated(String string)
    {
        editor.putBoolean(IS_CHANGED, true);
        editor.putString(AVATAR, string);
        editor.commit();
    }

    public void logout() {
        if(this.isLogin()) {
            editor.clear();
            editor.commit();
        }
    }
}
