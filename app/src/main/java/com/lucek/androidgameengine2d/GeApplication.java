package com.lucek.androidgameengine2d;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class GeApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return applicationContext;
    }
}
