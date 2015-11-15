package com.example.think.uihealth.app;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.kermit.exutils.utils.ExUtils;
import com.kermit.exutils.utils.LogUtils;

/**
 * Created by Zane on 2015/9/22.
 */
public class App extends Application {

    private static App instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ExUtils.initialize(this);
        Fresco.initialize(this);
        LogUtils.DEBUG = true;
    }


    public static Context getInstance() {
        return instance;
    }
}