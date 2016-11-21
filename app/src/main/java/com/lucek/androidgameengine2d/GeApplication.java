package com.lucek.androidgameengine2d;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class GEApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/go3.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public static Context getAppContext() {
        return applicationContext;
    }
}
