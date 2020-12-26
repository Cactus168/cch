package com.jo.cch.utils;

import android.app.Application;
import android.graphics.Typeface;

public class AppApplication extends Application {

    public static Typeface wordTypeFace;

    @Override
    public void onCreate() {
        super.onCreate();
        wordTypeFace = Typeface.createFromAsset(getAssets(), "fonnts/hwkt.ttf");
    }
}
