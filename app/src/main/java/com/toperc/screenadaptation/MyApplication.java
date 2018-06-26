package com.toperc.screenadaptation;

import android.app.Application;

/**
 * Created by toperc on 2018/5/20.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ScreenUtil.resetDensity(this);
    }

}
