package com.mo.essam.testtask.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by Lincoln on 05/05/16.
 */
public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences constants
    private static final String PREF_NAME = "MyPreference";
    private static final String IS_LOGGED_IN = "IsLoggedIn";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setIsLoggedIn(boolean loginFlag){
        editor.putBoolean(IS_LOGGED_IN,loginFlag);
        editor.commit();
    }

    public boolean getIsLoggedIn(){
        return pref.getBoolean(IS_LOGGED_IN,false);
    }


}
