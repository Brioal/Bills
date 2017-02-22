package com.brioal.bills.base;

import android.app.Application;
import android.graphics.Typeface;

import java.lang.reflect.Field;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/21.
 */

public class BaseApplication extends Application {

    private Typeface mTypeface;

    @Override
    public void onCreate() {
        super.onCreate();
        mTypeface = Typeface.createFromAsset(getAssets(), "fz.ttf");
        try {
            Field field = Typeface.class.getDeclaredField("SERIF");
            field.setAccessible(true);
            field.set(null, mTypeface);
        } catch (Exception e) {
        }
    }
}
