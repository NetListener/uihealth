package com.example.think.uihealth.app;

import android.app.Application;
import android.content.Context;

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
    }


    public static Context getInstance() {
        return instance;
    }
}