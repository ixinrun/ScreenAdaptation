package com.toperc.screenadaptation;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //一定要写在setContentView之前
        ScreenUtil.resetDensity(this);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //屏幕旋转时会使一些参数初始化，所以也需要在此重置一下
        ScreenUtil.resetDensity(this);
    }
}
