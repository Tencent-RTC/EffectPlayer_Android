package com.tencent.demo.tceffectplayer;
import android.app.Application;
import android.content.Context;


public class DemoApplication extends Application {
    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this.getApplicationContext();
    }

    public static Context getAppContext() {
        return mAppContext;
    }
}