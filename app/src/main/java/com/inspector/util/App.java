package com.inspector.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by leandro on 28/08/15.
 */
public class App extends Application {

    private static App singleton;

    public App() {
        super();

        this.singleton = this;
    }

    public static Context getContext() {
        return singleton;
    }

    public static SharedPreferences getPreferences() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        return prefs;
    }
}
