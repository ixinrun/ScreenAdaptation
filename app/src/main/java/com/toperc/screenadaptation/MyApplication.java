package com.toperc.screenadaptation;

import android.app.Application;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * Created by 31718 on 2018/5/20.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        resetDensity(Configuration.ORIENTATION_PORTRAIT);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        resetDensity(newConfig.orientation);
    }

    /**
     * 重置屏幕密度
     *
     * @param orientation
     */
    private void resetDensity(int orientation) {
        //绘制页面时参照的设计图尺寸
        final float DESIGN_WIDTH = 1080f;
        final float DESIGN_HEIGHT = 1920f;
        final float DESTGN_INCH = 5.0f;
        //大屏调节因子，范围0~1，因同比例放大造成大屏幕视图显示非常傻大憨，用于调节感官度。
        final float BIG_SCREEN_FACTOR = 0.2f;

        DisplayMetrics dm = getResources().getDisplayMetrics();
        //确定放大缩小比率
        float rate = 1f;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            rate = dm.widthPixels / DESIGN_WIDTH;
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rate = dm.widthPixels / DESIGN_HEIGHT;
        }
        //确定参照屏幕密度
        float referenceDensity = (float) Math.sqrt(DESIGN_WIDTH * DESIGN_WIDTH + DESIGN_HEIGHT * DESIGN_HEIGHT) / DESTGN_INCH / 160;
        //确定最终屏幕密度
        float relativeDensity = referenceDensity * rate;
        if (relativeDensity > dm.density) {
            relativeDensity = relativeDensity - (relativeDensity - dm.density) * BIG_SCREEN_FACTOR;
        }
        dm.density = relativeDensity;
    }


}
