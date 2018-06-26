package com.toperc.screenadaptation;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        ScreenAdaptationUtil.resetDensity(this);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ScreenAdaptationUtil.resetDensity(this.getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
