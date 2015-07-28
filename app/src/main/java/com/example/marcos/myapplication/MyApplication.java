package com.example.marcos.myapplication;

import android.app.Application;

import com.example.marcos.myapplication.util.AppUtil;

/**
 * Created by Marcos on 23/07/2015.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        AppUtil.CONTEXT = getApplicationContext();
        super.onCreate();
    }
}
