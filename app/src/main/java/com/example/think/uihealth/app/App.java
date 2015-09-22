package com.example.think.uihealth.app;

import android.app.Application;
import android.content.Context;

import com.jude.utils.JUtils;

/**
 * Created by think on 2015/9/22.
 */
public class App extends Application {

    private static App instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        JUtils.initialize(this);
    }


    public static Context getInstance() {

        return instance;
    }
}