package com.example.harihara_medicals.utils;

import android.app.Application;
import android.content.Context;

import com.devs.acr.AutoErrorReporter;

public class MyApp extends Application {
    private static MyApp mApp = null;

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferencesManager.init(this);
        AutoErrorReporter.get(this)
                .setEmailAddresses("santhoshkumar@radssoon.com")
                .setEmailSubject("Auto Crash Report")
                .start();
        mApp = this;
    }

    public static Context context() {
        return mApp.getApplicationContext();
    }
}
